# Micro Dummy con SpringBoot & Apache Camel

## Controller (NotificationController):

- POST `/api/notify` - Recibe notificaciones y las envía a la ruta Camel direct:process-notification
- GET `/api/notify` - Consulta notificaciones desde archivos usando la ruta Camel direct:get-file-notifications

## Rutas Camel (NotificationRoute):

- `direct:process-notification` - Procesa la notificación, guarda en BD y envía a almacenamiento de archivos
- `direct:store-file` - Almacena la notificación en archivos JSON individuales
- `direct:get-file-notifications` - Lee todas las notificaciones de los archivos

## Contexto del Flujo

- El `controller` recibe la petición HTTP
- Se usa `ProducerTemplate` para enviar datos a las rutas Camel
- Las rutas procesan la lógica de negocio y almacenamiento
- El `controller` devuelve la respuesta al cliente

> Los datos de prueba se almacenan en `./out/notifications.json`

## Ejemplo de Prueba

```bash
# Enviar notificación
curl -X POST http://localhost:8081/api/notify \
-H "Content-Type: application/json" \
-d '{"message":"OK"}'

# Ver notificaciones en archivos
curl http://localhost:8081/api/notify
```
