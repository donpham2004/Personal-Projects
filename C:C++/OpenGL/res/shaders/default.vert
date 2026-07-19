// Source code for changing vertex position

// Version
#version 460 core

// Takes input at location 0 and reads a 3 dimensional vector inside aPos
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec3 aColor;
layout (location = 2) in vec2 aTex;
layout (location = 3) in vec3 aNormal;
// out puts color in form of vec3
out vec3 color;
out vec2 texCoord;
out vec3 Normal;
out vec3 crntPos;

uniform mat4 camMatrix;
uniform vec3 meshPos;
void main()
{
   vec4 tmp = vec4(aPos,1.0) + vec4(meshPos,1.0);
   crntPos = vec3(tmp.x,tmp.y,tmp.z);
   // sets gl_Position with homogenous vec4
   gl_Position = camMatrix * vec4(crntPos.x, crntPos.y , crntPos.z,1.0);
   color = aColor;
   texCoord = aTex;
   tmp = vec4(aNormal,1.0);
   Normal = vec3(tmp.x, tmp.y, tmp.z);
}