package com.example.VkBot.services;

import com.example.VkBot.models.VkMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class VkService {

    private final String accessToken;
    private final RestTemplate restTemplate;

    // Конструктор класса VkService, принимает accessToken из конфигурационного файла
    public VkService(@Value("${vk.access_token}") String accessToken) {
        this.accessToken = accessToken;
        this.restTemplate = new RestTemplate();
    }

    // Метод для обработки входящего сообщения
    public void handleMessage(VkMessage message) {
        // Формируем ответное сообщение
        String reply = "Вы сказали: " + message.getText();
        // Отправляем ответное сообщение
        sendMessage(message.getPeer_id(), reply);
    }

    // Метод для отправки сообщения пользователю
    private void sendMessage(int peerId, String message) {
        // Формируем URL для запроса к API VK с использованием UriComponentsBuilder
        URI url = UriComponentsBuilder.fromHttpUrl("https://api.vk.com/method/messages.send")
                .queryParam("peer_id", peerId) // Идентификатор пользователя или чата
                .queryParam("message", message) // Текст сообщения
                .queryParam("random_id", (int) (Math.random() * Integer.MAX_VALUE)) // Случайный идентификатор для предотвращения дублирования сообщений
                .queryParam("access_token", accessToken) // Токен доступа для авторизации
                .queryParam("v", "5.131") // Версия API VK
                .build()
                .toUri();

        try {
            // Выполняем GET-запрос к API VK и получаем ответ
            String response = restTemplate.getForObject(url, String.class);
            // Выводим ответ в консоль
            System.out.println("Sent message: " + response);
        } catch (Exception e) {
            // В случае ошибки выводим трассировку стека
            e.printStackTrace();
        }
    }
}
