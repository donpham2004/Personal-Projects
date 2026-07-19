#include "player.h"

void player_init(struct player *player, vec3 pos, float speed,
                 float sensitivity) {
  glm_vec3_zero(player->linearVelocity);
  glm_vec3_zero(player->keyVelocity);
  glm_vec3_copy(pos, player->bound.pos);

  player->bound.dim[2] = 0.5;
  player->bound.dim[1] = 2;
  player->bound.dim[0] = 0.5;
  player->speed = speed;
  player->sensitivity = sensitivity;
  player->firstClick = 1;
  player->flash = GL_FALSE;
  camera_init(&player->camera, pos);
}

void player_input(struct player *player, GLFWwindow *window) {
  static GLboolean escPressed = GL_FALSE;
  static GLboolean rightPressed = GL_FALSE;
  static GLboolean mouseLocked = GL_FALSE;
  int width, height;
  glfwGetWindowSize(window, &width, &height);
  glm_vec3_zero(player->keyVelocity);
  if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS) {
    if (!escPressed) {
      escPressed = GL_TRUE;
      mouseLocked ^= GL_TRUE;
      if (mouseLocked) {
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
        glfwSetCursorPos(window, width / 2.0, height / 2.0);
      } else
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    }
  } else {
    escPressed = GL_FALSE;
  }
  if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
    vec3 dPos;
    glm_vec3_copy(player->camera.orientation, dPos);
    dPos[1] = 0.0f;
    glm_normalize(dPos);
    glm_vec3_scale(dPos, player->speed, dPos);
    glm_vec3_add(dPos, player->keyVelocity, player->keyVelocity);
  }
  if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
    vec3 dPos;
    glm_vec3_copy(player->camera.orientation, dPos);
    dPos[1] = 0.0f;
    glm_normalize(dPos);
    glm_vec3_scale(dPos, -player->speed, dPos);
    glm_vec3_add(dPos, player->keyVelocity, player->keyVelocity);
  }
  if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
    vec3 dPos;
    glm_vec3_crossn(player->camera.orientation, player->camera.up, dPos);
    glm_vec3_scale(dPos, -1.0f * player->speed, dPos);
    glm_vec3_add(dPos, player->keyVelocity, player->keyVelocity);
  }
  if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
    vec3 dPos;
    glm_vec3_crossn(player->camera.orientation, player->camera.up, dPos);
    glm_vec3_scale(dPos, player->speed, dPos);
    glm_vec3_add(dPos, player->keyVelocity, player->keyVelocity);
  }
  if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS) {
    vec3 dPos;
    glm_vec3_scale(player->camera.up, player->speed, dPos);
    glm_vec3_add(dPos, player->keyVelocity, player->keyVelocity);
  }
  if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) {
    vec3 dPos;
    glm_vec3_scale(player->camera.up, -1.0 * player->speed, dPos);
    glm_vec3_add(dPos, player->keyVelocity, player->keyVelocity);
  }
  if (mouseLocked) {
    double mouseX, mouseY;
    float rotx, roty;
    vec3 newOrientation;
    vec3 right;

    glfwGetCursorPos(window, &mouseX, &mouseY);
    rotx = player->sensitivity * (float)(mouseY - (height / 2)) / height;
    roty = player->sensitivity * (float)(mouseX - (width / 2)) / width;

    glm_vec3_copy(player->camera.orientation, newOrientation);

    glm_vec3_crossn(player->camera.up, newOrientation, right);
    glm_vec3_rotate(newOrientation, glm_rad(rotx), right);
    if (!((glm_vec3_angle(newOrientation, player->camera.up) <=
           glm_rad(5.0f)) ||
          (glm_vec3_angle(newOrientation, player->camera.up) >=
           glm_rad(175.0f)))) {
      glm_vec3_copy(newOrientation, player->camera.orientation);
    }
    glm_vec3_rotate(player->camera.orientation, glm_rad(-roty),
                    player->camera.up);
    glfwSetCursorPos(window, width / 2.0, height / 2.0);
  }

  if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_RIGHT) == GLFW_PRESS) {
    if (!rightPressed)
      player->flash ^= GL_TRUE;
    rightPressed = GL_TRUE;
  } else if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_RIGHT) ==
             GLFW_RELEASE)
    rightPressed = GL_FALSE;
}

void player_tick(struct player *player) {
  glm_vec3_add(player->bound.pos, player->keyVelocity, player->bound.pos);
  glm_vec3_add(player->camera.pos, player->keyVelocity, player->camera.pos);
}
