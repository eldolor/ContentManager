package cm.cm.android.winecellar;


import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable
public class Wine implements Serializable {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private Long rowId;
	@Persistent
	private String wine;
	/** search index **/
	@Persistent
	private String wineIdx;
	
	@Persistent
	private String rating;
	@Persistent
	private String ratingIdx;
	
	@Persistent
	private String textExtract;
	@Persistent
	private String textExtractIdx;
	@Persistent
	private String notes;
	@Persistent
	private String uri;
	@Persistent
	private Long timeCreatedMs;
	@Persistent
	private Long timeCreatedTimeZoneOffsetMs;
	@Persistent
	private Long timeUpdatedMs;
	@Persistent
	private Long timeUpdatedTimeZoneOffsetMs;

	@Persistent
	public Boolean deleted = false;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRowId() {
		return rowId;
	}
	public void setRowId(Long rowId) {
		this.rowId = rowId;
	}
	public String getWine() {
		return wine;
	}
	public void setWine(String wine) {
		this.wine = wine;
	}
	public String getWineIdx() {
		return wineIdx;
	}
	public void setWineIdx(String wineIdx) {
		this.wineIdx = wineIdx;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getRatingIdx() {
		return ratingIdx;
	}
	public void setRatingIdx(String ratingIdx) {
		this.ratingIdx = ratingIdx;
	}
	public String getTextExtract() {
		return textExtract;
	}
	public void setTextExtract(String textExtract) {
		this.textExtract = textExtract;
	}
	public String getTextExtractIdx() {
		return textExtractIdx;
	}
	public void setTextExtractIdx(String textExtractIdx) {
		this.textExtractIdx = textExtractIdx;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public Long getTimeCreatedMs() {
		return timeCreatedMs;
	}
	public void setTimeCreatedMs(Long timeCreatedMs) {
		this.timeCreatedMs = timeCreatedMs;
	}
	public Long getTimeCreatedTimeZoneOffsetMs() {
		return timeCreatedTimeZoneOffsetMs;
	}
	public void setTimeCreatedTimeZoneOffsetMs(Long timeCreatedTimeZoneOffsetMs) {
		this.timeCreatedTimeZoneOffsetMs = timeCreatedTimeZoneOffsetMs;
	}
	public Long getTimeUpdatedMs() {
		return timeUpdatedMs;
	}
	public void setTimeUpdatedMs(Long timeUpdatedMs) {
		this.timeUpdatedMs = timeUpdatedMs;
	}
	public Long getTimeUpdatedTimeZoneOffsetMs() {
		return timeUpdatedTimeZoneOffsetMs;
	}
	public void setTimeUpdatedTimeZoneOffsetMs(Long timeUpdatedTimeZoneOffsetMs) {
		this.timeUpdatedTimeZoneOffsetMs = timeUpdatedTimeZoneOffsetMs;
	}

	public Boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	

}