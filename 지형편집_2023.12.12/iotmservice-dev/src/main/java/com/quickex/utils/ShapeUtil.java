package com.quickex.utils;

import com.quickex.domain.doc.UserCulturalHeritageCreateSHPModel;
import com.vividsolutions.jts.geom.*;
import org.geotools.data.FeatureWriter;
import org.geotools.data.Transaction;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ShapeUtil {


    public static void write2Shape(String shpPath, String encode, String geoType, List<UserCulturalHeritageCreateSHPModel> geoms) {
        try {

            File file = new File(shpPath);

            File folderFile = new File(file.getParent());
            if (!folderFile.exists() && !folderFile.isDirectory()) {
                folderFile.mkdirs();
            }

            Map<String, Serializable> params = new HashMap<>();
            params.put(ShapefileDataStoreFactory.URLP.key, file.toURI().toURL());
            ShapefileDataStore ds = (ShapefileDataStore) new ShapefileDataStoreFactory().createNewDataStore(params);
            //
            SimpleFeatureTypeBuilder tb = new SimpleFeatureTypeBuilder();
            tb.setCRS(DefaultGeographicCRS.WGS84);
            tb.setName("shapefile");

            if ("Polygon".equals(geoType)) {  //
                tb.add("the_geom", Polygon.class);
            } else if ("MultiPolygon".equals(geoType)) {  //
                tb.add("the_geom", MultiPolygon.class);
            } else if ("Point".equals(geoType)) {  //
                tb.add("the_geom", Point.class);
            } else if ("MultiPoint".equals(geoType)) {   //
                tb.add("the_geom", MultiPoint.class);
            } else if ("LineString".equals(geoType)) {   //
                tb.add("the_geom", LineString.class);
            } else if ("MultiLineString".equals(geoType)) {
                tb.add("the_geom", MultiLineString.class);   //
            } else {
                throw new Exception("Geometry：" + geoType);
            }

            tb.add("areaName", String.class);
            tb.add("planeValue", String.class);
            tb.add("tiltValue", String.class);
            tb.add("epsg", String.class);

            ds.createSchema(tb.buildFeatureType());
            //
            Charset charset = Charset.forName(encode);
            ds.setCharset(charset);
            //
            FeatureWriter<SimpleFeatureType, SimpleFeature> writer = ds.getFeatureWriter(ds.getTypeNames()[0], Transaction.AUTO_COMMIT);

            for (UserCulturalHeritageCreateSHPModel item : geoms) {
                //String type = geom.getGeometryType();

                //
                SimpleFeature feature = writer.next();

                //wkt
                feature.setAttribute("the_geom", item.getGeom());

                //user data
                feature.setAttribute("areaName", item.getAreaName());
                feature.setAttribute("planeValue", item.getPlaneValue());
                feature.setAttribute("tiltValue", item.getTiltValue());
                feature.setAttribute("epsg", item.getEpsg());
            }
            writer.write();
            writer.close();
            ds.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void zipShapeFile(String shpPath) {
        try {
            File shpFile = new File(shpPath);


            File folderFile = new File(shpFile.getParent());
            if (!folderFile.exists() && !folderFile.isDirectory()) {
                folderFile.mkdirs();
            }

            String shpRoot = shpFile.getParentFile().getPath();
            String shpName = shpFile.getName().substring(0, shpFile.getName().lastIndexOf("."));

            String zipPath = shpRoot + File.separator + shpName + ".zip";
            File zipFile = new File(zipPath);
            InputStream input = null;
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            //
            zipOut.setComment(shpName);
            String[] shpFiles = new String[]{
                    shpRoot + File.separator + shpName + ".dbf",
                    shpRoot + File.separator + shpName + ".prj",
                    shpRoot + File.separator + shpName + ".shp",
                    shpRoot + File.separator + shpName + ".shx",
                    shpRoot + File.separator + shpName + ".fix"
            };

            for (int i = 0; i < shpFiles.length; i++) {
                File file = new File(shpFiles[i]);
                input = new FileInputStream(file);
                zipOut.putNextEntry(new ZipEntry(file.getName()));
                int temp = 0;
                while ((temp = input.read()) != -1) {
                    zipOut.write(temp);
                }
                input.close();
            }
            zipOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
