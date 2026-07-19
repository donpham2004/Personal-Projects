#ifndef PLAYER_H
#define PLAYER_H
#include <glad/glad.h>
#include <GLFW/glfw3.h>
#include <cglm/cglm.h>
#include "camera.h"
#include "bounding_box.h"

struct player
{
    struct bounding_box bound;
    struct camera camera;
    vec3 linearVelocity;
    vec3 keyVelocity;
    float speed;
    float sensitivity;
    GLboolean flash;
    int firstClick;
};

void player_init(struct player *player, vec3 pos, float speed, float sensitivity);

void player_input(struct player *player, GLFWwindow *window);
void player_tick(struct player *player);

void player_clamp_to_bound(struct player *player);
#endif