/*
 * Crown Copyright (C) 2019 Dstl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.gov.dstl.geo.osgb;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EastingNorthingConversionTest {
  private static final double LAT_AIRY1830 = 52.657570305555552;
  private static final double LON_AIRY1830 = 1.7179215833333332;

  private static final double EASTING = 651409.903;
  private static final double NORTHING = 313177.270;

  @Test
  public void testFromLatLon() {
    double[] output =
        EastingNorthingConversion.fromLatLon(
            new double[] {LAT_AIRY1830, LON_AIRY1830},
            Constants.ELLIPSOID_AIRY1830_MAJORAXIS,
            Constants.ELLIPSOID_AIRY1830_MINORAXIS,
            Constants.NATIONALGRID_N0,
            Constants.NATIONALGRID_E0,
            Constants.NATIONALGRID_F0,
            Constants.NATIONALGRID_LAT0,
            Constants.NATIONALGRID_LON0);

    assertEquals(EASTING, output[0], 0.0005);
    assertEquals(NORTHING, output[1], 0.0005);
  }

  @Test
  public void testToLatLon() {
    double[] output =
        EastingNorthingConversion.toLatLon(
            new double[] {EASTING, NORTHING},
            Constants.ELLIPSOID_AIRY1830_MAJORAXIS,
            Constants.ELLIPSOID_AIRY1830_MINORAXIS,
            Constants.NATIONALGRID_N0,
            Constants.NATIONALGRID_E0,
            Constants.NATIONALGRID_F0,
            Constants.NATIONALGRID_LAT0,
            Constants.NATIONALGRID_LON0);

    assertEquals(LAT_AIRY1830, output[0], 0.0005);
    assertEquals(LON_AIRY1830, output[1], 0.0005);
  }
}
