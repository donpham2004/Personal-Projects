#define GLFW_INCLUDE_NONE // Included so GLFW and glad headers can be in any
                          // order, if not included, glad should be first
#include "box.h"
#include "camera.h"
#include "light.h"
#include "material.h"
#include "mesh.h"
#include "physic.h"
#include "player.h"
#include "portal.h"
#include "vertex.h"
#include <GLFW/glfw3.h> // Header for GLFW, a cross platform API for interfacing with OpenGL, Vulcan, etc
#include <cglm/cglm.h>
#include <errno.h>
#include <glad/glad.h> // Header for GLAD, a loader for newer advanced opengl functions
#include <math.h>
#include <stdio.h>
#include <string.h>

#define WIDTH 1920
#define HEIGHT 1440
#define SIZE 1

static struct box *init_maze(const char grid[8], struct box *floor, int *size) {
  box_init(floor, (vec3){0, -0.5, 0}, 8, 1, 8);
  box_material_init(floor, "/home/donpham/OpenGL/res/textures/oak.png",
                    "/home/donpham/OpenGL/res/textures/oakspec.jpeg");
  *size = 0;
  for (int i = 0; i < 8; i++) {
    for (int j = 0; j < 8; j++)
      if (grid[i] & 1 << j)
        (*size)++;
  }
  struct box *boxes = malloc(*size * sizeof(struct box));
  int start = 0;
  for (int i = 0; i < 8; i++) {
    for (int j = 0; j < 8; j++) {
      if (grid[i] & 1 << j) {
        box_init(boxes + start, (vec3){-3.5 + i, 1, -3.5 + j}, 1, 2, 1);
        box_material_init(boxes + start,
                          "/home/donpham/OpenGL/res/textures/oak.png",
                          "/home/donpham/OpenGL/res/textures/oakspec.jpeg");
        start++;
      }
    }
  }
  return boxes;
}

void error_callback(int error, const char *description) {
  fprintf(stderr, "Error: %s\n", description);
}

int main(void) {
  GLFWwindow *window;
  if (!glfwInit()) // Initializes GLFW Library. GLFW_TRUE means Successful and
                   // GLFW_FALSE doesn't, 1 and 0 respectively
    return 1;
  // Tells window minimum opengl version needed on system
  glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 2);
  glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
  glfwSetErrorCallback(
      error_callback); // Sets function to be called when function errors happen
  window =
      glfwCreateWindow(WIDTH, HEIGHT, "Title", NULL,
                       NULL); // Creates window handle and its opengl context
  if (!window)                // returns null on error
    goto end;
  glfwMakeContextCurrent(
      window);  // sets the opengl window to be the current context
  gladLoadGL(); // Load modern OpenGL functions

  // Sets size of drawing canvas
  glViewport(0, 0, WIDTH, HEIGHT);
  glEnable(GL_CULL_FACE);
  glCullFace(GL_FRONT);
  glFrontFace(GL_CCW);
  glEnable(GL_DEPTH_TEST);
  glDepthFunc(GL_LESS);
  glEnable(GL_STENCIL_TEST);
  glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);
  printf("%s\n", glGetString(GL_VERSION));

  struct player player;
  int numLights = 1;
  struct light lights[1];
  struct box floor;
  struct box *walls;
  int size = 0;
  struct light_vertex lightVertices[] = {
      // Coords
      {-0.5f, -0.5f, 0.5f}, {-0.5f, -0.5f, -0.5f}, {0.5f, -0.5f, -0.5f},
      {0.5f, -0.5f, 0.5f},  {-0.5f, 0.5f, 0.5f},   {-0.5f, 0.5f, -0.5f},
      {0.5f, 0.5f, -0.5f},  {0.5f, 0.5f, 0.5f}};

  GLuint lightIndices[] = {// Top
                           4, 5, 6, 4, 6, 7,
                           // Bottom
                           0, 2, 1, 0, 3, 2,

                           // Front
                           0, 4, 7, 0, 7, 3,

                           // Back
                           1, 6, 5, 1, 2, 6,

                           // Left
                           1, 5, 4, 1, 4, 0,

                           // Right

                           2, 7, 6, 2, 3, 7};
  light_init(lights, (vec4){1.0, 1.0, 1.0, 1.0}, (vec3){0.0f, 5.0f, 0.0f});
  light_shader_init(lights);

  player_init(&player, (vec3){0.0f, 5.0f, 0.0f}, 0.05, 80.0f);
  // walls = init_maze((const char[]){
  // 0b11101111,
  // 0b10001011,
  // 0b11100001,
  // 0b10001001,
  // 0b10111011,
  // 0b10101001,
  // 0b10001111,
  // 0b11101111,
  //},
  //&floor, &size);
  // Returns true if the window should close flag is set
  int width = WIDTH;
  int height = HEIGHT;

  box_init(&floor, (vec3){0, -0.5, 0}, 1, 1, 1);
  box_material_init(&floor, "/home/donpham/OpenGL/res/textures/oak.png",
                    "/home/donpham/OpenGL/res/textures/oakspec.jpeg");

  /* struct portal portal; */
  /* portal_end_init(portal.ends, (vec3){0, 2, 1}, (vec3){0, glm_rad(180), 0},
   * 2, 4); */
  /* portal_end_init(portal.ends + 1, (vec3){10, 2, 1}, (vec3){0, 0, 0}, 2, 4);
   */
  while (!glfwWindowShouldClose(window)) {
    // Specify background color
    glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

    // Clean back buffer and assign the new color to it
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
    glfwGetWindowSize(window, &width, &height);
    glViewport(0, 0, width, height);
    player_input(&player, window);
    vec3 dir[3] = {{player.keyVelocity[0], 0, 0},
                   {0, player.keyVelocity[1], 0},
                   {0, 0, player.keyVelocity[2]}};
    for (int i = 0; i < 3; i++) {
      fix_collision(&player.bound, dir[i], &floor.bound);
      for (int j = 0; j < size; j++)
        fix_collision(&player.bound, dir[i], &(walls + j)->bound);
      glm_vec3_copy(dir[i], player.keyVelocity);
      player_tick(&player);
    }
    glm_vec3_fill(lights[0].color, player.flash);
    camera_matrix(&player.camera, glm_rad(90.0f), 0.01, 100.f, width, height);
    glStencilMask(0x0);
    box_render(&floor, &player.camera, lights, numLights);
    for (int i = 0; i < size; i++)
      box_render(walls + i, &player.camera, lights, numLights);
    for (int i = 0; i < numLights; i++)
      light_render(lights + i, &player.camera);

    /* portal_render(&portal, &player.camera); */
    /* glm_vec3_copy(portal.ends[0].pos, player.camera.pos); */
    box_render(&floor, &player.camera, lights, numLights);
    for (int i = 0; i < size; i++)
      box_render(walls + i, &player.camera, lights, numLights);
    for (int i = 0; i < numLights; i++)
      light_render(lights + i, &player.camera);

    // Swap rendering buffer and display buffer

    glfwSwapBuffers(window);
    glfwPollEvents(); // Receives all events by GLFW
  }
  // Delete our resources
  box_delete(&floor);
  for (int i = 0; i < numLights; i++)
    light_delete(lights + i);
  glfwDestroyWindow(window); // Destroys window
end:
  glfwTerminate(); // Destroys all GLFW resources
}
