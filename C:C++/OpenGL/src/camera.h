#ifndef _CAMERA_H_
#define _CAMERA_H_
#include <glad/glad.h>
#include <cglm/cglm.h>
#include <GLFW/glfw3.h>
#include "shader.h"
#include "uniform.h"

struct camera
{
    mat4 cam;
    vec3 pos;
    vec3 orientation;
    vec3 up;
};

void camera_init(struct camera *camera, vec3 pos);

void camera_matrix(struct camera *camera, float fov, float near, float far, int width, int height);

#endif