/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.meteoinfo.chart.jogl;

import org.meteoinfo.chart.plot3d.GraphicCollection3D;
import org.meteoinfo.global.Extent3D;
import org.meteoinfo.legend.ColorBreak;
import org.meteoinfo.legend.LegendScheme;
import org.meteoinfo.legend.PolygonBreak;
import org.meteoinfo.shape.PointZ;

/**
 *
 * @author yaqiang
 */
public class SurfaceGraphics extends GraphicCollection3D {
    private PointZ[][] vertices;
    private int[][] legendIndex;
    
    /**
     * Constructor
     */
    public SurfaceGraphics() {
        super();        
        this.allQuads = true;
        this.singleLegend = false;
    }
    
    /**
     * Get vertices
     * @return 
     */
    public PointZ[][] getVertices() {
        return this.vertices;
    }
    
    /**
     * Set vertices
     * @param value Vertices 
     */
    public void setVertices(PointZ[][] value) {
        this.vertices = value;
        double xmin, ymin, zmin, xmax, ymax, zmax;
        xmin = ymin = zmin = Double.MAX_VALUE;
        xmax = ymax = zmax = Double.MIN_VALUE;
        PointZ p;
        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[0].length; j++) {
                p = value[i][j];
                xmin = xmin > p.X ? p.X : xmin;
                ymin = ymin > p.Y ? p.Y : ymin;
                zmin = zmin > p.Z ? p.Z : zmin;
                xmax = xmax < p.X ? p.X : xmax;
                ymax = ymax < p.Y ? p.Y : ymax;
                zmax = zmax < p.Z ? p.Z : zmax;
            }
        }
        Extent3D extent = new Extent3D(xmin, xmax, ymin, ymax, zmin, zmax);
        this.setExtent(extent);
    }
    
    /**
     * Get vertex
     * @param i Vertex index i
     * @param j Vertex index j
     * @return Vertex
     */
    public PointZ getVertex(int i, int j) {
        return this.vertices[i][j];
    }
    
    /**
     * Get dimension 1
     * @return Dimension 1
     */
    public int getDim1() {
        return this.vertices.length;
    }
    
    /**
     * Get dimension 2
     * @return Dimension 2
     */
    public int getDim2() {
        return this.vertices[0].length;
    }
    
    /**
     * Set legend scheme
     *
     * @param value Legend scheme
     */
    @Override
    public void setLegendScheme(LegendScheme value) {
        this.legendScheme = value;
        this.updateLegendIndex();
    }
    
    /**
     * Update legend index
     */
    public void updateLegendIndex() {
        int dim1 = this.getDim1();
        int dim2 = this.getDim2();
        this.legendIndex = new int[dim1][dim2];
        for (int i = 0; i < dim1; i++) {
            for (int j = 0; j < dim2; j++) {
                this.legendIndex[i][j] = this.legendScheme.legendBreakIndex(this.vertices[i][j].M);
            }
        }
    }
    
    /**
     * Get legend break of a vertex
     * @param i Vertex index i
     * @param j Vertex index j
     * @return Legend break
     */
    public ColorBreak getLegendBreak(int i, int j) {
        return this.legendScheme.getLegendBreak(this.legendIndex[i][j]);
    }
    
    /**
     * Get RGBA array of a vertex
     * @param i Vertex index i
     * @param j Vertex index j
     * @return RGBA float array
     */
    public float[] getRGBA(int i, int j) {
        return this.legendScheme.getLegendBreak(this.legendIndex[i][j]).getColor().getRGBComponents(null);
    }
 
}