#version 460 core

// Output the fragment color

out vec4 FragColor;

// Access color output from default.vert
in vec3 color;
in vec2 texCoord;
in vec3 Normal;
in vec3 crntPos;


// Access uniform input from shader call

// textures are given automatically
uniform sampler2D tex0;
uniform sampler2D tex1;

uniform vec3 camPos;
uniform vec3 orientation;

struct light {
   vec3 lightColor;
   vec3 lightPos;
};

#define NUM_LIGHTS 1
uniform light lights[NUM_LIGHTS];


vec4 pointLight() {
   vec4 sum = texture(tex0,texCoord) * 0.05;
   for(int i=0;i<NUM_LIGHTS;i++){ 
      vec3 lightColor = lights[i].lightColor;
      vec3 lightPos = lights[i].lightPos;
      vec3 lightVector = lightPos -crntPos;
      float dist = length(lightVector);
      float a = 0.01f;
      float b = 0.0f;
      float inten = 1.0f/(a*dist*dist + b*dist +1.0f);
      float ambient = 0.2f;
      vec3 normal = normalize(Normal);
      vec3 lightDirection = normalize(lightVector);
      float diffuse = max(dot(orientation, lightDirection),0.0f);

      float specularLight = 0.5f;
      vec3 reflectionDirection = reflect(-lightDirection, normal);
      float specAmount = pow(max(dot(orientation, reflectionDirection),0.0f),16);
      float specular = specAmount * specularLight;
      sum+= (texture(tex0, texCoord) * (diffuse*inten) + texture(tex1, texCoord).r*specular*inten)*vec4(lightColor,1);
   }
   return sum;
}

vec4 directLight() {
   float ambient = 0.2f;
   vec4 sum = texture(tex0,texCoord) * ambient;
   for(int i=0;i<NUM_LIGHTS;i++){ 
      vec3 lightColor = lights[i].lightColor;
      vec3 lightPos = lights[i].lightPos;
      vec3 normal = normalize(Normal);
      vec3 lightDirection = normalize(vec3(0.0f,1.0f,0.0f));
      float diffuse = max(dot(normal, lightDirection),0.0f);

      float specularLight = 0.5f;
      vec3 viewDirection = normalize(camPos - crntPos);
      vec3 reflectionDirection = reflect(-lightDirection, normal);
      float specAmount = pow(max(dot(viewDirection, reflectionDirection),0.0f),16);
      float specular = specAmount * specularLight;
      sum +=(texture(tex0, texCoord) * (diffuse) + texture(tex1, texCoord).r*specular)*vec4(lightColor,1);
   }
   return sum;
}

vec4 spotLight() {

   vec4 sum;
   for(int i=0;i<NUM_LIGHTS;i++){ 
      vec3 lightColor = lights[i].lightColor;
      vec3 lightPos = lights[i].lightPos;
      float inner = 0.75f;
      float outer = 0.6f;
      float ambient = 0.2f;

      vec3 normal = normalize(Normal);
      vec3 lightDirection = normalize(lightPos-crntPos);
      float diffuse = max(dot(normal, lightDirection),0.0f);

      float specularLight = 0.5f;
      vec3 viewDirection = normalize(camPos - crntPos);
      vec3 reflectionDirection = reflect(-lightDirection, normal);
      float specAmount = pow(max(dot(viewDirection, reflectionDirection),0.0f),16);
      float specular = specAmount * specularLight;

      float angle = dot(orientation,-lightDirection);
      float inten = clamp((angle-outer)/(inner-outer),0.0f,1.0f);
      sum += (texture(tex0, texCoord) * (diffuse*inten) + texture(tex1, texCoord).r*specular*inten)*vec4(lightColor,1.0f);
   }
   return sum;
}
//
//float near = 0.1f;
//float far =100.0f;
//
//float linearizeDepth(float depth) {
//   return (2.0* near* far)/(far + near - (depth*2.0 - 1.0)*(far-near));
//}

void main()
{
   FragColor= directLight();
}