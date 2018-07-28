package com.ep.eventparticipant.object;

import org.litepal.crud.DataSupport;

/**
 * @author As_
 * @since 2018/07/24
 * @email apknet@163.com
 * @github https://github.com/apknet
 *
 */

public class EventBean extends DataSupport {

    private int id;
    private String name;
    private String startTime;
    private String endTime;
    private String where;
    private String note;
    private String imgUri;
    private int organizerId;
    private String organizerHeader;
    private String organizerName;
    private String organizerTel;
    private String organizerNote;
    private int personCount;
    private String personNames;
    private boolean isJoin;

    public EventBean(int id, String name, String startTime, String endTime, String where, String note, String imgUri, int organizerId,
                     String organizerHeader, String organizerName, String organizerTel, String organizerNote, int personCount, String personNames, boolean isJoin) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.where = where;
        this.note = note;
        this.imgUri = imgUri;
        this.organizerId = organizerId;
        this.organizerHeader = organizerHeader;
        this.organizerName = organizerName;
        this.organizerTel = organizerTel;
        this.organizerNote = organizerNote;
        this.personCount = personCount;
        this.personNames = personNames;
        this.isJoin = isJoin;
    }

    public EventBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public int getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(int organizerId) {
        this.organizerId = organizerId;
    }

    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    public String getPersonNames() {
        return personNames;
    }

    public void setPersonNames(String personNames) {
        this.personNames = personNames;
    }

    public boolean isJoin() {
        return isJoin;
    }

    public void setJoin(boolean join) {
        isJoin = join;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public String getOrganizerTel() {
        return organizerTel;
    }

    public void setOrganizerTel(String organizerTel) {
        this.organizerTel = organizerTel;
    }

    public String getOrganizerNote() {
        return organizerNote;
    }

    public String getOrganizerHeader() {
        return organizerHeader;
    }

    public void setOrganizerHeader(String organizerHeader) {
        this.organizerHeader = organizerHeader;
    }

    public void setOrganizerNote(String organizerNote) {
        this.organizerNote = organizerNote;
    }
}
