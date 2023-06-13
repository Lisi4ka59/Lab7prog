package com.lisi4ka.validation;

import com.lisi4ka.utils.PackagedCommand;

public interface Validation {
    PackagedCommand[] valid(String[] commandText);
}
