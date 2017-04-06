package com.zireck.remotecraft.dagger.qualifiers;

import java.lang.annotation.Retention;
import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Retention(RUNTIME)
public @interface PlayerAvatarUrl {
}
