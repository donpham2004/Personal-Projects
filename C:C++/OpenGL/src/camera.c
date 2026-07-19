#include "camera.h"
#include <math.h>
void camera_init(struct camera *camera, vec3 pos)
{
    glm_vec3_copy(pos, camera->pos);
    camera->orientation[0] = 0.0f;
    camera->orientation[1] = 0.0f;
    camera->orientation[2] = -1.0f;
    camera->up[0] = 0.0f;
    camera->up[1] = 1.0f;
    camera->up[2] = 0.0f;
}

void camera_matrix(struct camera *camera, float fov, float near, float far, int width, int height)
{
    mat4 view = GLM_MAT4_IDENTITY_INIT;
    mat4 proj = GLM_MAT4_IDENTITY_INIT;
    vec3 center;
    glm_vec3_add(camera->pos, camera->orientation, center);
    glm_lookat(camera->pos, center, camera->up, view);
    glm_perspective(fov, (float)width / height, near, far, proj);
    glm_mul(proj, view, camera->cam);
}
