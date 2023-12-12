package com.quickex.utils;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.*;
import lombok.extern.slf4j.Slf4j;
import org.geotools.geojson.geom.GeometryJSON;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

@Slf4j
public class WKTUtil {

    public static String geomToWkt(Geometry geometry) {
        String wkt = null;
        WKTWriter writer = new WKTWriter();
        wkt = writer.write(geometry);
        return wkt;
    }

    public static String geomToWkbHex(Geometry geometry) {
        String wkb = null;
        WKBWriter wkbWriter = new WKBWriter();
        byte[] write = wkbWriter.write(geometry);
        wkb = WKBWriter.toHex(write);
        return wkb;
    }

    public static Geometry wktToGeom(String wkt) throws ParseException {
        Geometry geometry = null;
        WKTReader reader = new WKTReader();
        geometry = reader.read(wkt);
        //geometry.setSRID(4326);
        return geometry;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static Geometry geoJsonToGeometry(String geoJson) {
        GeometryJSON json = new GeometryJSON();
        Reader reader = new StringReader(geoJson);
        try {
            return json.read(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String wktToJson(String wkt) {
        String json = null;
        try {
            WKTReader reader = new WKTReader();
            Geometry geometry = reader.read(wkt);
            StringWriter writer = new StringWriter();
            GeometryJSON g = new GeometryJSON(20);
            g.write(geometry, writer);
            json = writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }





    private static byte[] hexToBytes(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }


    public static String wkbStrToWKT(String wkbString){
        byte[] wkbBytes = hexToBytes(wkbString);
        WKBReader wkbReader = new WKBReader();
        Geometry geometry = null;
        try {
            geometry = wkbReader.read(wkbBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return geomToWkt(geometry);
    }


}
