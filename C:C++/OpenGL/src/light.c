#include "light.h"
#include "uniform.h"
void light_init(struct light *light, vec4 color, vec3 pos)
{
    light->mesh = NULL;
    light->shader = NULL;
    glm_vec4_copy(color, light->color);
    glm_vec3_copy(pos, light->pos);
}

void light_vertex_init(struct light *light, struct light_vertex *vertices, GLsizeiptr vsize, GLuint *indices, GLsizeiptr isize)
{
    light->mesh = malloc(sizeof(struct mesh));
    array_buffer_init(&light->mesh->array_buffer);
    vertex_buffer_init(&light->mesh->vertex_buffer, (GLfloat *)vertices, vsize);
    element_buffer_init(&light->mesh->element_buffer, indices, isize);
    array_buffer_link_attrb(&light->mesh->vertex_buffer, 0, sizeof(vertices->coords) / sizeof(GLfloat), GL_FLOAT, sizeof(struct light_vertex), NULL);
    vertex_buffer_unbind();
    array_buffer_unbind();
    element_buffer_unbind();
}

void light_shader_init(struct light *light)
{
    light->shader = malloc(sizeof(struct shader));
    shader_init(light->shader, "/home/donpham/OpenGL/res/shaders/light.vert", "/home/donpham/OpenGL/res/shaders/light.frag");
}

void light_render(struct light *light, struct camera *camera)
{
    if (light->shader != NULL && light->mesh != NULL)
    {
        mesh_bind(light->mesh);
        shader_use(light->shader);
        glUniformMatrix4fv(glGetUniformLocation(light->shader->id, CAMERA_MATRIX), 1, GL_FALSE, (const GLfloat *)camera->cam);
        glUniform3fv(glGetUniformLocation(light->shader->id, LIGHT_POS), 1, light->pos);
        glUniform4fv(glGetUniformLocation(light->shader->id, LIGHT_COLOR), 1, light->color);
        glDrawElements(GL_TRIANGLES, light->mesh->element_buffer.size / sizeof(GLuint), GL_UNSIGNED_INT, NULL);
    }
}

void light_delete(struct light *light)
{
    if (light->mesh != NULL)
        mesh_delete(light->mesh);
    if (light->shader != NULL)
        shader_destroy(light->shader);
}
