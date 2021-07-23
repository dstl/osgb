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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class NationalGridTest {

  private static final String NG_COMPACT = "NW1623434223";
  private static final String NG_EXPANDED = "NW 16234 34223";
  // Invalid as different lengths of thing
  private static final String NG_INVALID = "NW134534223";

  private static final double E = 116234;
  private static final double N = 534223;

  @Test
  public void testFrom() {
    double[] en = NationalGrid.fromNationalGrid(NG_COMPACT);
    assertEquals(E, en[0], 0.001);
    assertEquals(N, en[1], 0.001);

    double[] enExpanded = NationalGrid.fromNationalGrid(NG_EXPANDED);
    assertEquals(E, enExpanded[0], 0.001);
    assertEquals(N, enExpanded[1], 0.001);
  }

  @Test
  public void testFromInvalid() {
    assertThrows(IllegalArgumentException.class, () -> NationalGrid.fromNationalGrid(NG_INVALID));
  }

  @Test
  public void testTo() {
    Optional<String> ng = NationalGrid.toNationalGrid(new double[] {E, N});

    assertEquals(NG_EXPANDED, ng.orElseThrow());
  }

  @Test
  public void testExample() {
    assertConversion("TG 51409 13177", 651409.0, 313177.0);
    assertConversion("NN 166 712", 216600.0, 771200.0);
    assertConversion("NN 126 729", 212600.0, 772900.0);
    assertConversion("SU 21940 45374", 421940.0, 145374.0);
    assertConversion("SU 02194 45374", 402194.0, 145374.0);
  }

  @Test
  public void testSplitOnWhitespace(){
    List<String> ret = NationalGrid.splitOnWhitespace(" a b\tc  d ");

    assertEquals(4, ret.size());
    assertEquals("a", ret.get(0));
    assertEquals("b", ret.get(1));
    assertEquals("c", ret.get(2));
    assertEquals("d", ret.get(3));
  }

  private void assertConversion(String example, double exE, double exN) {
    double[] out = NationalGrid.fromNationalGrid(example);
    assertEquals(exE, out[0], 0.001);
    assertEquals(exN, out[1], 0.001);

    Optional<String> ng = NationalGrid.toNationalGrid(new double[] {exE, exN});
    assertCoordinateEquals(example, ng.orElseThrow());
  }

  private void assertCoordinateEquals(String expected, String actual) {
    String coordExpected = expected.substring(2);
    String coordActual = actual.substring(2);

    List<String> listExpected = NationalGrid.splitOnWhitespace(coordExpected.substring(2));
    List<String> listActual = NationalGrid.splitOnWhitespace(coordActual.substring(2));

    String normalizedExpected = null;
    String normalizedActual = null;

    if (listActual.size() >= 2) {
      normalizedExpected =
          expected.substring(0, 2)
              + addZeroes(listExpected.get(0))
              + addZeroes(listExpected.get(1));
      normalizedActual =
          expected.substring(0, 2) + addZeroes(listActual.get(0)) + addZeroes(listActual.get(1));
    } else if (listActual.size() == 1) {
      int index = listExpected.get(0).length() / 2;
      normalizedExpected =
          expected.substring(0, 2)
              + addZeroes(listExpected.get(0).substring(0, index))
              + addZeroes(listExpected.get(0).substring(index));

      index = listActual.get(0).length() / 2;
      normalizedActual =
          actual.substring(0, 2)
              + addZeroes(listActual.get(0).substring(0, index))
              + addZeroes(listActual.get(0).substring(index));
    } else {
      fail("Unable to parse coordinate");
    }

    assertEquals(normalizedExpected.toUpperCase(), normalizedActual.toUpperCase());
  }

  private String addZeroes(String s) {
    StringBuilder t = new StringBuilder(s);
    while (t.length() < 5) {
      t.append("0");
    }

    return t.toString();
  }
}
