package net.thequester.websupport.model;

/**
 * Created by Tomo.
 */
public class QuestDetails {

    private int id;
    private String questName;
    private String description;
    private double latitude;
    private double longitude;
    private QuestType questType;
    private String url;
	private String owner;

    public QuestDetails() {
    }

    public QuestDetails(int id,
						String questName,
						String description,
						double latitude,
						double longitude,
						QuestType questType,
						String url,
						String owner) {
        this.id = id;
        this.questName = questName;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.questType = questType;
        this.url = url;
		this.owner = owner;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
