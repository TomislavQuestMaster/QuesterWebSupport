package net.thequester.websupport.model;

/**
 * Created by Tomo.
 */
public class Filter {

	private Double latitude;
	private Double longitude;
	private Double radius;

	private String name;
	private String description;
	private String owner;

	public Filter() {

	}

	public Filter(double latitude, double longitude, double radius) {

		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
	}

	public Double getLatitude() {

		return latitude;
	}

	public void setLatitude(Double latitude) {

		this.latitude = latitude;
	}

	public Double getLongitude() {

		return longitude;
	}

	public void setLongitude(Double longitude) {

		this.longitude = longitude;
	}

	public Double getRadius() {

		return radius;
	}

	public void setRadius(Double radius) {

		this.radius = radius;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getDescription() {

		return description;
	}

	public void setDescription(String description) {

		this.description = description;
	}

	public String getOwner() {

		return owner;
	}

	public void setOwner(String owner) {

		this.owner = owner;
	}
}
