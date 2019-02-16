package com.develop.perlasoft.di;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class,AsistentesRepositoryModule.class,RoomDbModule.class})
public interface AppComponent {
}
