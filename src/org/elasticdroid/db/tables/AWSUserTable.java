package org.elasticdroid.db.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "AWSUserTable")
@SuppressWarnings("unused")
public class AWSUserTable {
	
	public static final String TBL_NAME = "AWSUserTable";
	
	@DatabaseField(unique=true, canBeNull = false)
	private String awsUsername;
	
	@DatabaseField(unique=true, canBeNull = false)
	private String awsAccessKey;
	
	@DatabaseField(unique=true, canBeNull = false)
	private String awsSecretAccessKey;
	
	public String getAwsUsername() {
		return awsUsername;
	}

	public void setAwsUsername(String awsUsername) {
		this.awsUsername = awsUsername;
	}

	public String getAwsAccessKey() {
		return awsAccessKey;
	}

	public void setAwsAccessKey(String awsAccessKey) {
		this.awsAccessKey = awsAccessKey;
	}

	public String getAwsSecretAccessKey() {
		return awsSecretAccessKey;
	}

	public void setAwsSecretAccessKey(String awsSecretAccessKey) {
		this.awsSecretAccessKey = awsSecretAccessKey;
	}
	
	public boolean isNull() {
		return awsUsername == null || awsAccessKey == null || awsSecretAccessKey == null;
	}

	public AWSUserTable() {
		
	}
}
