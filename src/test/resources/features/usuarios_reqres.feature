Feature: Gestión de usuarios en la API ReqRes

  Scenario: Listar usuarios exitosamente
    Given que el servicio de usuarios de ReqRes está disponible
    When el actor consulta la lista de usuarios de la página 2
    Then la respuesta debe ser exitosa y contener al menos un usuario

  Scenario: Registrar usuario exitosamente
    Given que el servicio de registro de ReqRes está disponible
    When el actor registra un usuario con correo y contraseña válidos
    Then la respuesta de registro debe ser exitosa y contener id y token

  Scenario: Registrar usuario sin contraseña
    Given que el servicio de registro de ReqRes está disponible
    When el actor intenta registrar un usuario sin contraseña
    Then la respuesta debe indicar el error Missing password

  Scenario: Actualizar un usuario existente
    Given que el servicio de actualización de usuarios de ReqRes está disponible
    When el actor actualiza el nombre y el trabajo del usuario con id 2
    Then la respuesta de actualización debe ser exitosa y contener name, job y updatedAt

  Scenario: Eliminar un usuario existente
    Given que el servicio de eliminación de usuarios de ReqRes está disponible
    When el actor elimina el usuario con id 2
    Then la respuesta de eliminación debe ser exitosa con código 204