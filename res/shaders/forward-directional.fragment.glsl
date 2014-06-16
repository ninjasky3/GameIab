#version 120
#include "lighting.fh.glsl"

uniform DirectionalLight R_directionalLight;

vec4 CalcLightingEffect(vec3 normal, vec3 worldPos) {
	return CalcDirectionalLight(R_directionalLight, normal, worldPos);
}

#include "lightingMain.fh.glsl"
