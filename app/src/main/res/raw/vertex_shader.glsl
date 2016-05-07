attribute vec4 a_Vertex;

uniform mat4 u_View;
uniform mat4 u_Projection;
uniform vec4 u_Translation;

void main() {

    mat4 v_Translation = mat4(1, 0, 0, 0,
                              0, 1, 0, 0,
                              0, 0, 1, 0,
                              u_Translation.x, u_Translation.y, u_Translation.z, 1);


    gl_Position = u_Projection * u_View * v_Translation * a_Vertex;
}
