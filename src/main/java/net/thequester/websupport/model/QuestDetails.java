package net.thequester.websupport.model;

/**
 * Created by Tomo.
 */

import net.thequester.model.Quest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="quests")
public class QuestDetails {

    @Id
    @GeneratedValue
    private Long id;

    private String questName;
    private String description;
    private double latitude;
    private double longitude;
    private QuestType questType;
    private String url;
	private String owner;

    public QuestDetails() {
    }

    public QuestDetails(Long id,
						String questName,
						String description,
						double latitude,
						double longitude,
						QuestType questType,
						String url,
						String owner) {
        this.questName = questName;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.questType = questType;
        this.url = url;
		this.owner = owner;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestName() {
        return questName;
    }

    public void setQuestName(String questName) {
        this.questName = questName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public QuestType getQuestType() {
        return questType;
    }

    public void setQuestType(QuestType questType) {
        this.questType = questType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

	public String getOwner() {

		return owner;
	}

	public void setOwner(String owner) {

		this.owner = owner;
	}

	public static class Builder{

		private QuestDetails details;

		public Builder() {
			 details = new QuestDetails();
		}

		public Builder at(Double lat, Double lon){
			details.setLatitude(lat);
			details.setLongitude(lon);
			return this;
		}

		public QuestDetails build(){
			return details;
		}
	}
}
