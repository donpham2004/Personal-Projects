#version 460 core

layout (location = 0) in vec3 aPos;
layout (location = 1) in vec3 aColor;
layout (location = 2) in vec2 aTex;
layout (location = 3) in vec3 aNormal;
uniform mat4 camMatrix;
uniform vec3 meshPos;
uniform mat3 orientation;
void main() {
    gl_Position = camMatrix * vec4(orientation*aPos + meshPos,1);
}