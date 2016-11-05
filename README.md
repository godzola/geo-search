Geo Search
=============

This is a basic JAVA search model for Geographic areas. It tries yo do something useful with some standard technologies, PostGres, POSTGIS and the Java Topological suite. The data I'm currently using is gadm v2.8.

I exposed two ways lookup information, one by name and the other by lat/lon.  Since we're using gadm, we typically have names for each place in 7 languages, Arabic, Chinese, English, French, Spanish and Russian.  The name search can use any of the 7 orthographies to return the same result.  The lat/lon lookup takes a point on the globe and returns information about all the geographic areas that contain the point in question.

I currently have a "FakeMain" class. What's there should become tests, and the lookups should become endpoints in a REST service. At least that's my plan.

There are a couple of chopice points here. I'm returning JSON, but I could easily return GeoJSON. That would be standard.  Also, I read the geometries as WellKnownBinary from the database. I then store them as WellKnownText to do lookups.  I suppose you could envision an API where you could ask for the geometries in any number of formats, GeoJSON, WKB, WKT, KML, etc. 