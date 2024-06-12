package com.example.VkBot.controllers;

import com.example.VkBot.models.VkRequest;
import com.example.VkBot.services.VkService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VkController {

    private final VkService vkService;
    private final String confirmationToken;

    // Конструктор класса VkController, принимает VkService и confirmationToken из конфигурационного файла
    public VkController(VkService vkService, @Value("${vk.confirmation_token}") String confirmationToken) {
        this.vkService = vkService;
        this.confirmationToken = confirmationToken;
    }

    // Метод для обработки POST-запросов
    @PostMapping
    public String receiveUpdate(@RequestBody VkRequest vkRequest) {
        // В зависимости от типа запроса выполняем соответствующие действия
        switch (vkRequest.getType()) {
            case "confirmation":
                // Если тип запроса "confirmation", возвращаем confirmationToken для подтверждения
                return confirmationToken;
            case "message_new":
                // Если тип запроса "message_new", передаем сообщение в VkService для обработки
                vkService.handleMessage(vkRequest.getObject().getMessage());
                return "ok";
            default:
                // Для всех остальных типов запросов возвращаем "ok"
                return "ok";
        }
    }
}
