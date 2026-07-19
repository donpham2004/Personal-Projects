#include "portal.h"
#include "vertex.h"
#include "uniform.h"
void portal_end_init(struct portal_end *end, vec3 pos, vec3 direction, int width, int height)
{
    glm_vec3_copy(pos, end->pos);
    struct mesh_vertex vertices[] = {
        {{-0.5f * width, 0.5f * height, 0}, {0, 0, 0}, {0, 0}, {0, 0, 1}},
        {{0.5f * width, 0.5f * height, 0}, {0, 0, 0}, {0, 0}, {0, 0, 1}},
        {{-0.5f * width, -0.5f * height, 0}, {0, 0, 0}, {0, 0}, {0, 0, 1}},
        {{0.5f * width, -0.5f * height, 0}, {0, 0, 0}, {0, 0}, {0, 0, 1}}};
    GLuint indices[] = {
        0, 2, 1,
        2, 3, 1};
    shader_init(&end->shader, "/home/donpham/OpenGL/res/shaders/portal.vert", "/home/donpham/OpenGL/res/shaders/portal.frag");
    mesh_init(&end->mesh, vertices, sizeof(vertices), indices, sizeof(indices));
    glm_mat3_identity(end->orientation);
    glm_vec3_angle(direction, (vec3){0.0f, 0.0f, 1.0f});

    glm_rotate_x(end->orientation, direction[0], end->orientation);
    glm_rotate_y(end->orientation, direction[1], end->orientation);
    glm_rotate_z(end->orientation, direction[2], end->orientation);
}

void portal_end_render(struct portal_end *end, struct camera *camera)
{
    glStencilFunc(GL_ALWAYS, 1, 0xFF);
    glStencilMask(0xFF);
    mesh_bind(&end->mesh);
    shader_use(&end->shader);
    GLuint id = end->shader.id;
    glUniformMatrix4fv(glGetUniformLocation(id, CAMERA_MATRIX), 1, GL_FALSE, (const GLfloat *)camera->cam);
    glUniformMatrix3fv(glGetUniformLocation(id, ORIENTATION), 1, GL_FALSE, (const GLfloat *)end->orientation);
    glUniform3fv(glGetUniformLocation(id, MESH_POS), 1, end->pos);
    glDrawElements(GL_TRIANGLES, end->mesh.element_buffer.size / sizeof(GLuint), GL_UNSIGNED_INT, NULL);
    vec3 normal = {0, 0, 1};
    glm_mat3_mulv(end->orientation, normal, normal);
    vec3 dir;
    glm_vec3_add(normal, end->pos, dir);
}