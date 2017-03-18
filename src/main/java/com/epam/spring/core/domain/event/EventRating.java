package com.epam.spring.core.domain.event;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * @author alehstruneuski
 */
@XmlType(name = "rating")
@XmlEnum
public enum EventRating {

    LOW,
    MID,
    HIGH;

}
