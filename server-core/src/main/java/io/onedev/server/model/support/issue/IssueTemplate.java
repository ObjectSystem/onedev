package io.onedev.server.model.support.issue;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;

import io.onedev.server.web.component.issue.workflowreconcile.UndefinedFieldResolution;
import io.onedev.server.web.component.issue.workflowreconcile.UndefinedFieldValue;
import io.onedev.server.web.component.issue.workflowreconcile.UndefinedFieldValuesResolution;
import io.onedev.server.web.component.issue.workflowreconcile.UndefinedStateResolution;
import io.onedev.server.web.editable.annotation.Editable;
import io.onedev.server.web.editable.annotation.IssueQuery;
import io.onedev.server.web.editable.annotation.Markdown;
import io.onedev.server.web.editable.annotation.NameOfEmptyValue;

@Editable
public class IssueTemplate implements Serializable {

	private static final long serialVersionUID = 1L;

	private String issueDescription;
	
	private String issueQuery;

	@Editable(order=100)
	@Markdown
	@NotEmpty
	public String getIssueDescription() {
		return issueDescription;
	}

	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}

	@Editable(order=200, name="Applicable Issues", description="Optionally specify issues applicable for this template. "
			+ "Leave empty for all")
	@IssueQuery(withCurrentBuildCriteria = false, withCurrentCommitCriteria = false, 
			withCurrentPullRequestCriteria = false, withCurrentUserCriteria = false, 
			withOrder = false)
	@NameOfEmptyValue("All")
	public String getIssueQuery() {
		return issueQuery;
	}

	public void setIssueQuery(String issueQuery) {
		this.issueQuery = issueQuery;
	}
	
	public Set<String> getUndefinedStates() {
		Set<String> undefinedStates = new HashSet<>();
		if (issueQuery != null) {
			try {
				undefinedStates.addAll(io.onedev.server.search.entity.issue.IssueQuery.parse(
						null, issueQuery, false, true, true, true, true).getUndefinedStates());
			} catch (Exception e) {
			}
		}
		return undefinedStates;
	}

	public Set<String> getUndefinedFields() {
		Set<String> undefinedFields = new HashSet<>();
		if (issueQuery != null) {
			try {
				undefinedFields.addAll(io.onedev.server.search.entity.issue.IssueQuery
						.parse(null, issueQuery, false, true, true, true, true).getUndefinedFields());
			} catch (Exception e) {
			}
		}
		return undefinedFields;
	}
	
	public Collection<UndefinedFieldValue> getUndefinedFieldValues() {
		Collection<UndefinedFieldValue> undefinedFieldValues = new HashSet<>(); 
		if (issueQuery != null) {
			try {
				io.onedev.server.search.entity.issue.IssueQuery parsedQuery = 
						io.onedev.server.search.entity.issue.IssueQuery.parse(null, issueQuery, false, true, true, true, true);
				undefinedFieldValues.addAll(parsedQuery.getUndefinedFieldValues());
			} catch (Exception e) {
			}
		}
		return undefinedFieldValues;
	}
	
	public boolean fixUndefinedStates(Map<String, UndefinedStateResolution> resolutions) {
		if (issueQuery != null) {
			try {
				io.onedev.server.search.entity.issue.IssueQuery parsedQuery = 
						io.onedev.server.search.entity.issue.IssueQuery.parse(null, issueQuery, false, true, true, true, true);
				if (parsedQuery.fixUndefinedStates(resolutions))
					return true;
				else 
					issueQuery = parsedQuery.toString();
			} catch (Exception e) {
			}
		}
		return false;
	}
	
	public boolean fixUndefinedFields(Map<String, UndefinedFieldResolution> resolutions) {
		if (issueQuery != null) {
			try {
				io.onedev.server.search.entity.issue.IssueQuery parsedQuery = 
						io.onedev.server.search.entity.issue.IssueQuery.parse(null, issueQuery, false, true, true, true, true);
				if (parsedQuery.fixUndefinedFields(resolutions))
					return true;
				else
					issueQuery = parsedQuery.toString();
			} catch (Exception e) {
			}
		}
		return false;
	}
	
	public boolean fixUndefinedFieldValues(Map<String, UndefinedFieldValuesResolution> resolutions) {
		if (issueQuery != null) {
			try {
				io.onedev.server.search.entity.issue.IssueQuery parsedQuery = 
						io.onedev.server.search.entity.issue.IssueQuery.parse(null, issueQuery, false, true, true, true, true);
				if (parsedQuery.fixUndefinedFieldValues(resolutions))
					return true;
				else
					issueQuery = parsedQuery.toString();
			} catch (Exception e) {
			}
		}
		return false;
	}
	
}
