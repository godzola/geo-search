Geo Search
=============

This is a basic JAVA search model for Geographic areas. It tries yo do something useful with some standard technologies, PostGres, POSTGIS and the Java Topological suite. The data I'm currently using is gadm v2.8.

I exposed two ways lookup information, one by name and the other by lat/lon.  Since we're using gadm, we typically have names for each place in 7 languages, Arabic, Chinese, English, French, Spanish and Russian.  The name search can use any of the 7 orthographies to return the same result.  The lat/lon lookup takes a point on the globe and returns information about all the geographic areas that contain the point in question.

I currently have a "FakeMain" class. What's there should become tests, and the lookups should become endpoints in a REST service. At least that's my plan.

There are a couple of chopice points here. I'm returning JSON, but I could easily return GeoJSON. That would be standard.  Also, I read the geometries as WellKnownBinary from the database. I then store them as WellKnownText to do lookups.  I suppose you could envision an API where you could ask for the geometries in any number of formats, GeoJSON, WKB, WKT, KML, etc.

The observant reader will notice I didn't use Lucene, or another fancy library to do indexing/retrieval. After kicking it around my head for a day, I am still on the fence. The use case I described doesn't have stop words, or really require tokenization, but, case sensitivity might be an issue. Lucene has case insensitive tokenizers/analyzers and that might be worth using for an orthography like Cyrillic. I'll have to look more at what the JAVA std library can do here.