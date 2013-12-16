/*
 Copyright (c) 2013, Stephen Gold
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright
 notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 notice, this list of conditions and the following disclaimer in the
 documentation and/or other materials provided with the distribution.
 * Stephen Gold's name may not be used to endorse or promote products
 derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL STEPHEN GOLD BE LIABLE FOR ANY
 DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package jme3utilities.sky;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import java.util.logging.Logger;

/**
 * A star from a star catalog, used by MakeStarMaps.
 *
 * @author Stephen Gold <sgold@sonic.net>
 */
class Star
        implements Comparable {
    // *************************************************************************
    // constants

    /**
     * message logger for this class
     */
    final private static Logger logger = Logger.getLogger(Star.class.getName());
    // *************************************************************************
    // fields
    /**
     * id number in the catalog (>=1)
     */
    int entryId;
    /**
     * apparent brightness (inverted logarithmic scale)
     */
    float apparentMagnitude;
    /**
     * declination (radians north of the celestial equator, <=Pi/2, >=-Pi/2)
     */
    float declination;
    /**
     * right ascension (radians east of the vernal equinox, <2*Pi, >=0)
     */
    float rightAscension;
    // *************************************************************************
    // constructors

    /**
     * Instantiate a star.
     *
     * @param entryId id number in the catalog (>=1)
     * @param rightAscension radians east of the vernal equinox (<2*Pi, >=0)
     * @param declination radians north of the celestial equator (>=-Pi/2,
     * <=Pi/2)
     *
     * @param apparentMagnitude apparent brightness (inverted logarithmic scale)
     */
    Star(int entryId, float rightAscension, float declination,
            float apparentMagnitude) {
        assert entryId >= 1 : entryId;
        assert declination <= FastMath.HALF_PI : declination;
        assert declination >= -FastMath.HALF_PI : declination;
        assert rightAscension >= 0f : rightAscension;
        assert rightAscension < FastMath.TWO_PI : rightAscension;

        this.entryId = entryId;
        this.rightAscension = rightAscension;
        this.declination = declination;
        this.apparentMagnitude = apparentMagnitude;
    }
    // *************************************************************************
    // new methods exposed

    /**
     * Read the star's apparent brightness.
     *
     * @return magnitude (inverted logarithmic scale)
     */
    float getApparentMagnitude() {
        return apparentMagnitude;
    }

    /**
     * Calculate a star's position in a right-handed Cartesian equatorial
     * coordinate system where:
     *
     * +X points to the juncture of the meridian with the celestial equator
     *
     * +Y points to the east horizon (also on the celestial equator)
     *
     * +Z points to the celestial north pole
     *
     * @param siderealTime radians since sidereal midnight (>=0, <2*Pi)
     * @return a new unit vector
     */
    Vector3f getEquatorialLocation(float siderealTime) {
        assert siderealTime >= 0f : siderealTime;
        assert siderealTime < FastMath.TWO_PI : siderealTime;
        /*
         * Compute the hour angle.
         */
        float hourAngle = siderealTime - rightAscension;
        /*
         * Convert hour angle and declination to Cartesian coordinates.
         */
        float cosDec = FastMath.cos(declination);
        float cosHA = FastMath.cos(hourAngle);
        float sinDec = FastMath.sin(declination);
        float sinHA = FastMath.sin(hourAngle);
        float x = cosDec * cosHA;
        float y = -cosDec * sinHA;
        float z = sinDec;
        Vector3f result = new Vector3f(x, y, z);

        assert result.isUnitVector() : result;
        return result;
    }
    // *************************************************************************
    // Comparable methods

    /**
     * Compare this star to another star.
     *
     * @param object the other star
     * @return 0 if the stars are identical or not comparable
     */
    @Override
    public int compareTo(Object object) {
        if (object == null) {
            throw new NullPointerException("object should not be null");
        }
        
        if (object instanceof Star) {
            Star other = (Star) object;

            if (apparentMagnitude < other.apparentMagnitude) {
                return 1;
            } else if (apparentMagnitude > other.apparentMagnitude) {
                return -1;
            }
            if (rightAscension < other.rightAscension) {
                return 1;
            } else if (rightAscension > other.rightAscension) {
                return -1;
            }
            if (declination < other.declination) {
                return 1;
            } else if (declination > other.declination) {
                return -1;
            }
        }
        return 0;
    }
    // *************************************************************************
    // Object methods

    /**
     * Test whether this star is equivalent to another.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Star) {
            return compareTo(other) == 0;
        }
        return false;
    }

    /**
     * Calculate a hash code for the object.
     */
    @Override
    public int hashCode() {
        float sum = apparentMagnitude + rightAscension + declination;
        int code = Float.valueOf(sum).hashCode();
        return code;
    }
}