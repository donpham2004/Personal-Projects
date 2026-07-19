#version 460 core

layout (location = 0) in vec3 pos;

uniform vec3 lightPos;
uniform mat4 camMatrix;

void main() {
    gl_Position = camMatrix * vec4(pos.x + lightPos.x, pos.y + lightPos.y, pos.z + lightPos.z,1.0f);
}