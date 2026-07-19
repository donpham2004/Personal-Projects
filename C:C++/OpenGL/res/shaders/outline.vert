#version 460 core

layout (location = 0) in vec3 aPos;

uniform mat4 camMatrix;
uniform vec3 meshPos;
uniform float outlining;

void main() {
    vec3 crntPos = outlining*aPos + meshPos;
    gl_Position = camMatrix * vec4(crntPos, 1);
}