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

public class CartesianConversionTest {
  private static final double LAT_AIRY1830 = 52.657570305555552;
  private static final double LON_AIRY1830 = 1.7179215833333332;
  private static final double ELL_AIRY1830 = 24.7;

  private static final double X_CART = 3874938.850;
  private static final double Y_CART = 116218.624;
  private static final double Z_CART = 5047168.207;

  @Test
  public void testConvertToCartesian() {
    double[] output =
        CartesianConversion.fromLatLon(
            new double[] {LAT_AIRY1830, LON_AIRY1830, ELL_AIRY1830},
            Constants.ELLIPSOID_AIRY1830_MAJORAXIS,
            Constants.ELLIPSOID_AIRY1830_MINORAXIS);

    assertEquals(X_CART, output[0], 0.0005);
    assertEquals(Y_CART, output[1], 0.0005);
    assertEquals(Z_CART, output[2], 0.0005);
  }

  @Test
  public void testConvertToLatLon() {
    double[] output =
        CartesianConversion.toLatLon(
            new double[] {X_CART, Y_CART, Z_CART},
            Constants.ELLIPSOID_AIRY1830_MAJORAXIS,
            Constants.ELLIPSOID_AIRY1830_MINORAXIS,
            0.00000001);

    assertEquals(LAT_AIRY1830, output[0], 0.00001);
    assertEquals(LON_AIRY1830, output[1], 0.00001);
    assertEquals(ELL_AIRY1830, output[2], 0.0005);
  }

  @Test
  public void testConvertToAndFro() {
    double[] cartesian =
        CartesianConversion.fromLatLon(
            new double[] {LAT_AIRY1830, LON_AIRY1830, ELL_AIRY1830},
            Constants.ELLIPSOID_AIRY1830_MAJORAXIS,
            Constants.ELLIPSOID_AIRY1830_MINORAXIS);
    double[] latlon =
        CartesianConversion.toLatLon(
            cartesian,
            Constants.ELLIPSOID_AIRY1830_MAJORAXIS,
            Constants.ELLIPSOID_AIRY1830_MINORAXIS,
            0.000001);

    assertEquals(LAT_AIRY1830, latlon[0], 0.00001);
    assertEquals(LON_AIRY1830, latlon[1], 0.00001);
    assertEquals(ELL_AIRY1830, latlon[2], 0.00001);
  }

  @Test
  public void testHelmertConversion() {
    double[] ht =
        CartesianConversion.helmertTransformation(
            new double[] {1, 2, 3}, 3.0, 2.0, 1.0, 0.5, 1.0, 2.0, 3.0);
    assertEquals(ht[0], 4.5, 0.0001);
    assertEquals(ht[1], 5.0, 0.0001);
    assertEquals(ht[2], 5.5, 0.0001);
  }
}
