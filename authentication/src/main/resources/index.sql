--Indexing for boardingrequest Table--
alter table boardingrequest add index boardingrequest_status_idx(status);
alter table boardingrequest add index boardingrequest_stateId_idx(stateId);
alter table boardingrequest add index boardingrequest_cityId_idx(cityId);
alter table boardingrequest add index boardingrequest_roleId_idx(roleId);
alter table boardingrequest add index boardingrequest_validateEnquiry_idx(validateEnquiry);


--Indexing for profile Table--
alter table profile add index profile_mobileNumber_idx(mobileNumber);
alter table profile add index profile_email_idx(emailId);
alter table profile add index profile_aadharNumber_idx(aadharNumber);
alter table profile add index profile_panNumber_idx(panNumber);


--Indexing for franchise Table--
alter table franchise add index profile_gstNo_idx(gstNo);
alter table franchise add index profile_licenseNo_idx(licenseNo);
alter table franchise add index profile_channelPartnerId_idx(channelPartnerId);
alter table franchise add index profile_panNumber_idx(panNumber);

--Indexing for profilerole Table--
alter table profilerole add index profileRole_roleId_idx(roleId);
alter table profilerole add index profileRole_isActive_idx(isActive);


--Indexing for address Table--
alter table address add index address_profileId_idx(profileId);


--Indexing for customerlead Table--
alter table customerlead add index customerlead_status_idx(status);
alter table customerlead add index customerlead_uploaded_by_idx(uploadedById);
alter table customerlead add index customerlead_assigned_idx(assignedId);

