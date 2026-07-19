
#include "box.h"

void box_init(struct box *box, vec3 pos, float width, float height, float depth)
{
    box->material = NULL;
    box->bound.dim[0] = width;
    box->bound.dim[1] = height;
    box->bound.dim[2] = depth;
    glm_vec3_copy(pos, box->bound.pos);
    glm_vec3_copy((vec3)GLM_VEC3_ZERO_INIT, box->linearVelocity);
    glm_vec3_copy((vec3)GLM_VEC3_ZERO_INIT, box->angularVelocity);
    struct mesh_vertex vertices[] = {

        // Bottom
        {{-0.5f * width, -0.5f * height, 0.5f * depth}, {1.0f, 0.0f, 0.0f}, {0.0f, (float)depth}, {0.0f, -1.0f, 0.0f}},
        {{-0.5f * width, -0.5f * height, -0.5f * depth}, {0.0f, 1.0f, 0.0f}, {0.0f, 0.0f}, {0.0f, -1.0f, 0.0f}},
        {{0.5f * width, -0.5f * height, -0.5f * depth}, {1.0f, 1.0f, 1.0f}, {(float)width, 0.0f}, {0.0f, -1.0f, 0.0f}},
        {{0.5f * width, -0.5f * height, 0.5f * depth}, {0.0f, 0.0f, 1.0f}, {(float)width, (float)depth}, {0.0f, -1.0f, 0.0f}},

        // Top
        {{-0.5f * width, 0.5f * height, 0.5f * depth}, {1.0f, 0.0f, 0.0f}, {0.0f, (float)depth}, {0.0f, 1.0f, 0.0f}},
        {{-0.5f * width, 0.5f * height, -0.5f * depth}, {0.0f, 1.0f, 0.0f}, {0.0f, 0.0f}, {0.0f, 1.0f, 0.0f}},
        {{0.5f * width, 0.5f * height, -0.5f * depth}, {1.0f, 1.0f, 1.0f}, {(float)width, 0.0f}, {0.0f, 1.0f, 0.0f}},
        {{0.5f * width, 0.5f * height, 0.5f * depth}, {0.0f, 0.0f, 1.0f}, {(float)width, (float)depth}, {0.0f, 1.0f, 0.0f}},

        // Left
        {{-0.5f * width, -0.5f * height, 0.5f * depth}, {1.0f, 0.0f, 0.0f}, {0.0f, (float)depth}, {-1.0f, 0.0f, 0.0f}},
        {{-0.5f * width, -0.5f * height, -0.5f * depth}, {0.0f, 1.0f, 0.0f}, {0.0f, 0.0f}, {-1.0f, 0.0f, 0.0f}},
        {{-0.5f * width, 0.5f * height, -0.5f * depth}, {1.0f, 1.0f, 1.0f}, {(float)height, 0.0f}, {-1.0f, 0.0f, 0.0f}},
        {{-0.5f * width, 0.5f * height, 0.5f * depth}, {0.0f, 0.0f, 1.0f}, {(float)height, (float)depth}, {-1.0f, 0.0f, 0.0f}},

        // Right
        {{0.5f * width, -0.5f * height, 0.5f * depth}, {1.0f, 0.0f, 0.0f}, {0.0f, (float)depth}, {1.0f, 0.0f, 0.0f}},
        {{0.5f * width, -0.5f * height, -0.5f * depth}, {0.0f, 1.0f, 0.0f}, {0.0f, 0.0f}, {1.0f, 0.0f, 0.0f}},
        {{0.5f * width, 0.5f * height, -0.5f * depth}, {1.0f, 1.0f, 1.0f}, {(float)height, 0.0f}, {1.0f, 0.0f, 0.0f}},
        {{0.5f * width, 0.5f * height, 0.5f * depth}, {0.0f, 0.0f, 1.0f}, {(float)height, (float)depth}, {1.0f, 0.0f, 0.0f}},

        // Front
        {{-0.5f * width, -0.5f * height, 0.5f * depth}, {1.0f, 0.0f, 0.0f}, {0.0f, (float)height}, {0.0f, 0.0f, 1.0f}},
        {{-0.5f * width, 0.5f * height, 0.5f * depth}, {0.0f, 1.0f, 0.0f}, {0.0f, 0.0f}, {0.0f, 0.0f, 1.0f}},
        {{0.5f * width, 0.5f * height, 0.5f * depth}, {1.0f, 1.0f, 1.0f}, {(float)width, 0.0f}, {0.0f, 0.0f, 1.0f}},
        {{0.5f * width, -0.5f * height, 0.5f * depth}, {0.0f, 0.0f, 1.0f}, {(float)width, (float)height}, {0.0f, 0.0f, 1.0f}},

        // Back
        {{-0.5f * width, -0.5f * height, -0.5f * depth}, {1.0f, 0.0f, 0.0f}, {0.0f, (float)height}, {0.0f, 0.0f, -1.0f}},
        {{-0.5f * width, 0.5f * height, -0.5f * depth}, {0.0f, 1.0f, 0.0f}, {0.0f, 0.0f}, {0.0f, 0.0f, -1.0f}},
        {{0.5f * width, 0.5f * height, -0.5f * depth}, {1.0f, 1.0f, 1.0f}, {(float)width, 0.0f}, {0.0f, 0.0f, -1.0f}},
        {{0.5f * width, -0.5f * height, -0.5f * depth}, {0.0f, 0.0f, 1.0f}, {(float)width, (float)height}, {0.0f, 0.0f, -1.0f}},

    };

    // Ordering of indexes of the vertices array to draw
    GLuint indices[] = {
        0, 2, 1, // Bottom
        0, 3, 2, // Bottom

        4, 5, 6, // Top
        4, 6, 7, // Top

        8, 9, 10,  // Left
        8, 10, 11, // Left

        12, 14, 13, // Right
        12, 15, 14, // Right

        16, 17, 18, // Front
        16, 18, 19, // Front

        20, 22, 21, // Back
        20, 23, 22, // Back
    };

    mesh_init(&box->mesh, vertices, sizeof(vertices), indices, sizeof(indices));
    shader_init(&box->shader, "/home/donpham/OpenGL/res/shaders/default.vert", "/home/donpham/OpenGL/res/shaders/default.frag");
}

void box_material_init(struct box *box, const char *diffusePath, const char *specularPath)
{
    box->material = malloc(sizeof(struct material));
    if (diffusePath != NULL)
        material_diffuse_init(box->material, diffusePath, GL_RGBA);
    if (specularPath != NULL)
        material_specular_init(box->material, specularPath, GL_R);
}

void box_render(struct box *box, struct camera *camera, struct light *lights, int numLights)
{
    mesh_bind(&box->mesh);
    shader_use(&box->shader);
    if (box->material != NULL)
        material_bind(box->material);
    glUniformMatrix4fv(glGetUniformLocation(box->shader.id, CAMERA_MATRIX), 1, GL_FALSE, (const GLfloat *)camera->cam);
    glUniform3fv(glGetUniformLocation(box->shader.id, MESH_POS), 1, box->bound.pos);
    glUniform3fv(glGetUniformLocation(box->shader.id, CAMERA_POS), 1, camera->pos);
    for (int i = 0; i < numLights; i++)
    {
        char buf[64];
        sprintf(buf, "lights[%d].lightColor", i);
        glUniform3fv(glGetUniformLocation(box->shader.id, buf), 1, lights[i].color);
        sprintf(buf, "lights[%d].lightPos", i);
        glUniform3fv(glGetUniformLocation(box->shader.id, buf), 1, lights[i].pos);
    }
    glUniform3fv(glGetUniformLocation(box->shader.id, ORIENTATION), 1, (const GLfloat *)camera->orientation);
    glDrawElements(GL_TRIANGLES, box->mesh.element_buffer.size / sizeof(GLuint), GL_UNSIGNED_INT, NULL);
}

void box_delete(struct box *box)
{
    mesh_delete(&box->mesh);
    if (box->material != NULL)
        material_destroy(box->material);
}