package com.backend.demo.service;

import com.backend.demo.entity.AnswerOption;
import com.backend.demo.repository.AnswerOptionRepository;
import org.springframework.stereotype.Service;

@Service
public class AnswerOptionService {
    private final AnswerOptionRepository answerOptionRepository;


    public AnswerOptionService(AnswerOptionRepository answerOptionRepository) {
        this.answerOptionRepository = answerOptionRepository;
    }

    public AnswerOption createAnswerOption(AnswerOption answerOption) {
        return answerOptionRepository.save(answerOption);
    }
}
