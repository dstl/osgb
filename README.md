# OSGB Library

Java library for converting OSGB ([Ordnance Survey National Grid](httphttps://www.ordnancesurvey.co.uk/documents/resources/guide-coordinate-systems-great-britain.pdf)) references and standard Latitude-Longitude (WGS84) coordinates.

## Usage

The following examples, taken from the unit tests, show how to use the library.
For more example, refer to the unit tests.

### Converting from WGS84 coordinates to OSGB

    double[] latlon = OSGB36.fromWGS84(51.5085300, -0.1257400);
    //latlon[0] = 51.508019, latlon[1] = -0.1241133
    
### Converting from OSGB coordinates to WGS84

    double[] latlon = OSGB36.toWGS84(51.5, 0.116667);
    //latlon[0] = 51.500514, latlon[1] = 0.115033

### Converting from OSGB Grid Reference to WGS84

    //Convert to Easting and Northing
    double[] eastingNorthing = NationalGrid.fromNationalGrid("NW 16234 34223");
    
    //Convert from Easting and Northing into Cartesian Coordinates (LatLon)
    double[] latlonOSGB38 = EastingNorthingConversion.toLatLon(
          eastingNorthing,
          Constants.ELLIPSOID_AIRY1830_MAJORAXIS,
          Constants.ELLIPSOID_AIRY1830_MINORAXIS,
          Constants.NATIONALGRID_N0,
          Constants.NATIONALGRID_E0,
          Constants.NATIONALGRID_F0,
          Constants.NATIONALGRID_LAT0,
          Constants.NATIONALGRID_LON0);
     
    //Convert from LatLon (OSGB) to WGS84
    double[] latlonWGS84 = OSGB36.toWGS84(latlonOSGB38[0], latlonOSGB38[1]);
    

