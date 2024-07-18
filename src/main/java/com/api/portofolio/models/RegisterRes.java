package com.api.portofolio.models;

import com.api.portofolio.models.entities.PortoUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRes extends LoginRes {
   PortoUser userDetails;
}
