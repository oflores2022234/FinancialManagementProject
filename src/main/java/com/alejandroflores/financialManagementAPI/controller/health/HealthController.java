package com.alejandroflores.financialManagementAPI.controller.health;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/")
    public String checkAPI() {
        return """
            <!DOCTYPE html>
            <html lang="es">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Financial Project by Franco Paiz</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f4f4f4;
                        margin: 0;
                        padding: 20px;
                    }
                    h1 {
                        color: #333;
                    }
                    p {
                        font-size: 18px;
                        color: #666;
                    }
                    .container {
                        max-width: 600px;
                        margin: auto;
                        background: #fff;
                        padding: 20px;
                        border-radius: 8px;
                        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>Financial Project by Franco Paiz</h1>
                    <p>
                        Esta aplicación es un sistema de gestión financiera que permite a los usuarios registrar ingresos y gastos, categorizarlos, y ver resúmenes y tendencias de sus finanzas. Su objetivo es ayudar a los usuarios a llevar un control eficaz de su situación financiera.
                    </p>
                </div>
            </body>
            </html>
        """;
    }
}