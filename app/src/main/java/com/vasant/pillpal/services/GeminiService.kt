package com.vasant.pillpal.services

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import com.vasant.pillpal.BuildConfig
import com.vasant.pillpal.repository.MedicineRepo
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiService @Inject constructor(
    private val medicineRepo: MedicineRepo
) {
    // The complex constructor you were trying to use is internal.
    private val generativeModel = GenerativeModel(
        // 1. Model name
        modelName = "gemini-2.5-flash",

        // 2. Your API Key from BuildConfig
        apiKey = BuildConfig.API_KEY,

        // 3. Configuration is set here
        generationConfig = generationConfig {
            temperature = 0.7f
            topK = 40
            topP = 0.95f
            maxOutputTokens = 1024
        },

        // 4. System instructions are set here
        systemInstruction = content {
            text(
                """
                You are a medical AI assistant for DoseFlow, a medication management app.
                
                Guidelines:
                - You will receive the user's current medication schedule with each query
                - Reference their specific medications when relevant to provide personalized advice
                - Provide clear, accurate medication information (dosages, interactions, side effects)
                - Answer health questions concisely with evidence-based responses
                - Use simple language; avoid medical jargon when possible
                - Check for potential drug interactions between medications in their list
                - For emergencies or serious symptoms, immediately advise: "Seek immediate medical attention"
                - Always include: "This is educational information only, not medical advice. Consult your healthcare provider."
                - Never diagnose conditions or prescribe medications
                
                Focus areas: medication reminders, drug interactions, adherence tips, general health guidance, symptom information.
            """.trimIndent()
            )
        }
    )


    /**
     * Formats user's medication list into a readable context for the AI
     */
    private suspend fun getMedicationContext(): String {
        return try {
            val medicines = medicineRepo.getMedicine().first()
            if (medicines.isEmpty()) {
                "\n\nUser Context: No medications currently scheduled."
            } else {
                val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
                val medicationList = medicines.joinToString("\n") { med ->
                    val timeStr = dateFormat.format(Date(med.time))
                    val status = if (med.isCompleted) "✓ Taken" else "⏰ Scheduled"
                    "- ${med.medName}: ${med.dosage} at $timeStr ($status)${med.note?.let { " - Note: $it" } ?: ""}"
                }
                "\n\nUser's Current Medications:\n$medicationList"
            }
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * Generates AI response with context about user's medications
     */
    suspend fun generateMedicalResponse(prompt: String): String {
        return try {
            // Get user's medication context
            val medicationContext = getMedicationContext()

            // Combine user prompt with medication context
            val enrichedPrompt = "$prompt$medicationContext"

            // Generate response with context
            val response = generativeModel.generateContent(enrichedPrompt)
            response.text ?: "I'm sorry, I couldn't generate a response. Please try again."
        } catch (e: Exception) {
            // Log the exception for debugging
            e.printStackTrace()
            // Return a user-friendly error message
            "An error occurred: ${e.message}"
        }
    }

    /**
     * Get a summary of user's medication schedule
     */
    suspend fun getMedicationSummary(): String {
        return try {
            val medicines = medicineRepo.getMedicine().first()
            if (medicines.isEmpty()) {
                "You don't have any medications scheduled yet. Would you like to add one?"
            } else {
                val total = medicines.size
                val completed = medicines.count { it.isCompleted }
                val pending = total - completed

                "You have $total medication(s) scheduled:\n" +
                "✓ Completed: $completed\n" +
                "⏰ Pending: $pending"
            }
        } catch (e: Exception) {
            "Unable to fetch medication summary."
        }
    }
}
