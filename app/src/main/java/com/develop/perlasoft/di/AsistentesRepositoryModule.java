package com.develop.perlasoft.di;

import com.develop.perlasoft.dao.AsistentesDAO;
import com.develop.perlasoft.repository.AsistentesRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = RoomDbModule.class)
public class AsistentesRepositoryModule {
}
