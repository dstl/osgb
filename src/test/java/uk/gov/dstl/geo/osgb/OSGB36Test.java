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
 */package uk.gov.dstl.geo.osgb;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OSGB36Test {
  @Test
  public void testFromWGS84() {
    double[] output = OSGB36.fromWGS84(51.5085300, -0.1257400);

    assertEquals(51.508019, output[0], 0.0001);
    assertEquals(-0.1241133, output[1], 0.0001);
  }

  @Test
  public void testToWGS84() {
    double[] output = OSGB36.toWGS84(51.5, 0.116667);

    assertEquals(51.500514, output[0], 0.0001);
    assertEquals(0.115033, output[1], 0.0001);
  }

  @Test
  public void testToAndFromWGS84() {
    double[] wgs84 = OSGB36.toWGS84(51.5, 0.116667);
    double[] osgb = OSGB36.fromWGS84(wgs84[0], wgs84[1]);

    assertEquals(51.5, osgb[0], 0.00001);
    assertEquals(0.116667, osgb[1], 0.00001);
  }
}
