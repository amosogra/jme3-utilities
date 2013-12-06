// (c) Copyright 2013 Stephen Gold <sgold@sonic.net>
// Distributed under the terms of the GNU General Public License

/*
 This file is part of the JME3 Utilities Package.

 The JME3 Utilities Package is free software: you can redistribute it and/or
 modify it under the terms of the GNU General Public License as published by the
 Free Software Foundation, either version 3 of the License, or (at your
 option) any later version.

 The JME3 Utilities Package is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 for more details.

 You should have received a copy of the GNU General Public License along with
 the JME3 Utilities Package.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * vertex shader used by dome62.j3md
 */
attribute vec2 inTexCoord;
attribute vec3 inPosition;
uniform mat4 g_WorldViewProjectionMatrix;
uniform vec2 m_TopCoord;
varying vec2 skyTexCoord;

#ifdef HAS_OBJECT0
        uniform vec2 m_Object0Center;
        uniform vec2 m_Object0TransformU;
        uniform vec2 m_Object0TransformV;
        varying vec2 object0Coord;
        varying vec2 object0Offset;
#endif

#ifdef HAS_OBJECT1
        uniform vec2 m_Object1Center;
        uniform vec2 m_Object1TransformU;
        uniform vec2 m_Object1TransformV;
        varying vec2 object1Coord;
        varying vec2 object1Offset;
#endif

#ifdef HAS_OBJECT2
        uniform vec2 m_Object2Center;
        uniform vec2 m_Object2TransformU;
        uniform vec2 m_Object2TransformV;
        varying vec2 object2Coord;
        varying vec2 object2Offset;
#endif

#ifdef HAS_OBJECT3
        uniform vec2 m_Object3Center;
        uniform vec2 m_Object3TransformU;
        uniform vec2 m_Object3TransformV;
        varying vec2 object3Coord;
        varying vec2 object3Offset;
#endif

#ifdef HAS_OBJECT4
        uniform vec2 m_Object4Center;
        uniform vec2 m_Object4TransformU;
        uniform vec2 m_Object4TransformV;
        varying vec2 object4Coord;
        varying vec2 object4Offset;
#endif

#ifdef HAS_OBJECT5
        uniform vec2 m_Object5Center;
        uniform vec2 m_Object5TransformU;
        uniform vec2 m_Object5TransformV;
        varying vec2 object5Coord;
        varying vec2 object5Offset;
#endif

#ifdef HAS_CLOUDS0
        uniform float m_Clouds0Scale;
        uniform vec2 m_Clouds0Offset;
        varying vec2 clouds0Coord;
#endif

#ifdef HAS_CLOUDS1
        uniform float m_Clouds1Scale;
        uniform vec2 m_Clouds1Offset;
        varying vec2 clouds1Coord;
#endif

void main(){
        skyTexCoord = inTexCoord;
        /*
         * The following cloud texture coordinate calculations must be kept
         * consistent with those in SkyMaterial.getTransparency(int,Vector2f) .
         */
        #ifdef HAS_CLOUDS0
                clouds0Coord = inTexCoord * m_Clouds0Scale + m_Clouds0Offset;
        #endif
        #ifdef HAS_CLOUDS1
                clouds1Coord = inTexCoord * m_Clouds1Scale + m_Clouds1Offset;
        #endif

        #ifdef HAS_OBJECT0
                object0Offset = inTexCoord - m_Object0Center;
                object0Coord.x = dot(m_Object0TransformU, object0Offset);
                object0Coord.y = dot(m_Object0TransformV, object0Offset);
                object0Coord += m_TopCoord;
        #endif
        #ifdef HAS_OBJECT1
                object1Offset = inTexCoord - m_Object1Center;
                object1Coord.x = dot(m_Object1TransformU, object1Offset);
                object1Coord.y = dot(m_Object1TransformV, object1Offset);
                object1Coord += m_TopCoord;
        #endif
        #ifdef HAS_OBJECT2
                object2Offset = inTexCoord - m_Object2Center;
                object2Coord.x = dot(m_Object2TransformU, object2Offset);
                object2Coord.y = dot(m_Object2TransformV, object2Offset);
                object2Coord += m_TopCoord;
        #endif
        #ifdef HAS_OBJECT3
                object3Offset = inTexCoord - m_Object3Center;
                object3Coord.x = dot(m_Object3TransformU, object3Offset);
                object3Coord.y = dot(m_Object3TransformV, object3Offset);
                object3Coord += m_TopCoord;
        #endif
        #ifdef HAS_OBJECT4
                object4Offset = inTexCoord - m_Object4Center;
                object4Coord.x = dot(m_Object4TransformU, object4Offset);
                object4Coord.y = dot(m_Object4TransformV, object4Offset);
                object4Coord += m_TopCoord;
        #endif
        #ifdef HAS_OBJECT5
                object5Offset = inTexCoord - m_Object5Center;
                object5Coord.x = dot(m_Object5TransformU, object5Offset);
                object5Coord.y = dot(m_Object5TransformV, object5Offset);
                object5Coord += m_TopCoord;
        #endif

        gl_Position = g_WorldViewProjectionMatrix * vec4(inPosition, 1);
}