package com.laboratorio.springboot67.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.moderation.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModerationService {
    private final ModerationModel moderationModel;

    public String getModerationResult(String message) {
        ModerationPrompt prompt = new ModerationPrompt(message);
        ModerationResponse response = this.moderationModel.call(prompt);
        Moderation moderation = response.getResult().getOutput();

        return this.getResults(moderation);
    }

    private String getResults(Moderation moderation) {
        StringBuilder moderationStr = new StringBuilder();

        moderationStr.append("Moderation results:\n");
        int i = 1;
        for (ModerationResult result : moderation.getResults()) {
            // Se recorren las categorías
            Categories categories = result.getCategories();
            CategoryScores scores = result.getCategoryScores();
            moderationStr.append(i).append("- Categories. Any category flagged: ").append(result.isFlagged());

            moderationStr.append("\n- Law: ")
                    .append(scores.getLaw())
                    .append(" - Flagged: ")
                    .append(categories.isLaw());

            moderationStr.append("\n- Financial: ")
                    .append(scores.getFinancial())
                    .append(" - Flagged: ")
                    .append(categories.isFinancial());

            moderationStr.append("\n- PII: ").append(scores.getPii()).append(" - Flagged: ").append(categories.isPii());
            moderationStr.append("\n- Sexual: ").append(scores.getSexual()).append(" - Flagged: ").append(categories.isSexual());
            moderationStr.append("\n- Hate: ").append(scores.getHate()).append(" - Flagged: ").append(categories.isHate());
            moderationStr.append("\n- Harassment: ").append(scores.getHarassment()).append(" - Flagged: ").append(categories.isHarassment());
            moderationStr.append("\n- Self-Harm: ").append(scores.getSelfHarm()).append(" - Flagged: ").append(categories.isSelfHarm());
            moderationStr.append("\n- Sexual/Minors: ").append(scores.getSexualMinors()).append(" - Flagged: ").append(categories.isSexualMinors());
            moderationStr.append("\n- Hate/Threatening: ").append(scores.getHateThreatening()).append(" - Flagged: ").append(categories.isHateThreatening());
            moderationStr.append("\n- Violence/Graphic: ").append(scores.getViolenceGraphic()).append(" - Flagged: ").append(categories.isViolenceGraphic());
            moderationStr.append("\n- Self-Harm/Intent: ").append(scores.getSelfHarmIntent()).append(" - Flagged: ").append(categories.isSelfHarmIntent());
            moderationStr.append("\n- Self-Harm/Instructions: ").append(scores.getSelfHarmInstructions()).append(" - Flagged: ").append(categories.isSelfHarmInstructions());
            moderationStr.append("\n- Harassment/Threatening: ").append(scores.getHarassmentThreatening()).append(" - Flagged: ").append(categories.isHarassmentThreatening());
            moderationStr.append("\n- Violence: ").append(scores.getViolence()).append(" - Flagged: ").append(categories.isViolence());
            moderationStr.append("\n- Health: ").append(scores.getHealth()).append(" - Flagged: ").append(categories.isHealth());
            moderationStr.append("\n- Dangerous/Criminal: ").append(scores.getDangerousAndCriminalContent()).append(" - Flagged: ").append(categories.isDangerousAndCriminalContent());

            i++;
        }

        return moderationStr.toString();
    }
}