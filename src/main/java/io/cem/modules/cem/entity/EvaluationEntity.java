package io.cem.modules.cem.entity;

import io.cem.common.utils.excel.annotation.ExportName;

public class EvaluationEntity {
    //网络连通性最高分
    @ExportName(exportName = "网络连通性最高分")
    private Double connectionMax;
    //网络连通性平均分
    @ExportName(exportName = "网络连通性平均分")
    private Double connectionAverage;
    //网络连通性最低分
    @ExportName(exportName = "网络连通性最低分")
    private Double connectionMin;
    //网络层质量最高分
    @ExportName(exportName = "网络层质量最高分")
    private Double qualityMax;
    //网络层质量平均分
    @ExportName(exportName = "网络层质量平均分")
    private Double qualityAverage;
    //网络层质量最低分
    @ExportName(exportName = "网络层质量最低分")
    private Double qualityMin;
    //文件下载最高分
    @ExportName(exportName = "文件下载最高分")
    private Double downloadMax;
    //文件下载平均分
    @ExportName(exportName = "文件下载平均分")
    private Double downloadAverage;
    //文件下载最低分
    @ExportName(exportName = "文件下载最低分")
    private Double downloadMin;
    //网页浏览最高分
    @ExportName(exportName = "网页浏览最高分")
    private Double pageMax;
    //网页浏览平均分
    @ExportName(exportName = "网页浏览平均分")
    private Double pageAverage;
    //网页浏览最低分
    @ExportName(exportName = "网页浏览最低分")
    private Double pageMin;
    //在线视频最高分
    @ExportName(exportName = "在线视频最高分")
    private Double videoMax;
    //在线视频平均分
    @ExportName(exportName = "在线视频平均分")
    private Double videoAverage;
    //在线视频最低分
    @ExportName(exportName = "在线视频最低分")
    private Double videoMin;
    //网络游戏最高分
    @ExportName(exportName = "网络游戏最高分")
    private Double gameMax;
    //网络游戏平均分
    @ExportName(exportName = "网络游戏平均分")
    private Double gameAverage;
    //网络游戏最低分
    @ExportName(exportName = "网络游戏最低分")
    private Double gameMin;

    public Double getConnectionMax() {
        return connectionMax;
    }

    public void setConnectionMax(Double connectionMax) {
        this.connectionMax = connectionMax;
    }

    public Double getConnectionAverage() {
        return connectionAverage;
    }

    public void setConnectionAverage(Double connectionAverage) {
        this.connectionAverage = connectionAverage;
    }

    public Double getConnectionMin() {
        return connectionMin;
    }

    public void setConnectionMin(Double connectionMin) {
        this.connectionMin = connectionMin;
    }

    public Double getQualityMax() {
        return qualityMax;
    }

    public void setQualityMax(Double qualityMax) {
        this.qualityMax = qualityMax;
    }

    public Double getQualityAverage() {
        return qualityAverage;
    }

    public void setQualityAverage(Double qualityAverage) {
        this.qualityAverage = qualityAverage;
    }

    public Double getQualityMin() {
        return qualityMin;
    }

    public void setQualityMin(Double qualityMin) {
        this.qualityMin = qualityMin;
    }

    public Double getDownloadMax() {
        return downloadMax;
    }

    public void setDownloadMax(Double downloadMax) {
        this.downloadMax = downloadMax;
    }

    public Double getDownloadAverage() {
        return downloadAverage;
    }

    public void setDownloadAverage(Double downloadAverage) {
        this.downloadAverage = downloadAverage;
    }

    public Double getDownloadMin() {
        return downloadMin;
    }

    public void setDownloadMin(Double downloadMin) {
        this.downloadMin = downloadMin;
    }

    public Double getPageMax() {
        return pageMax;
    }

    public void setPageMax(Double pageMax) {
        this.pageMax = pageMax;
    }

    public Double getPageAverage() {
        return pageAverage;
    }

    public void setPageAverage(Double pageAverage) {
        this.pageAverage = pageAverage;
    }

    public Double getPageMin() {
        return pageMin;
    }

    public void setPageMin(Double pageMin) {
        this.pageMin = pageMin;
    }

    public Double getVideoMax() {
        return videoMax;
    }

    public void setVideoMax(Double videoMax) {
        this.videoMax = videoMax;
    }

    public Double getVideoAverage() {
        return videoAverage;
    }

    public void setVideoAverage(Double videoAverage) {
        this.videoAverage = videoAverage;
    }

    public Double getVideoMin() {
        return videoMin;
    }

    public void setVideoMin(Double videoMin) {
        this.videoMin = videoMin;
    }

    public Double getGameMax() {
        return gameMax;
    }

    public void setGameMax(Double gameMax) {
        this.gameMax = gameMax;
    }

    public Double getGameAverage() {
        return gameAverage;
    }

    public void setGameAverage(Double gameAverage) {
        this.gameAverage = gameAverage;
    }

    public Double getGameMin() {
        return gameMin;
    }

    public void setGameMin(Double gameMin) {
        this.gameMin = gameMin;
    }
}
