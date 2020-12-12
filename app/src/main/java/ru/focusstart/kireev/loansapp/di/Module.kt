package ru.focusstart.kireev.loansapp.di

import android.content.Context
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import ru.focusstart.kireev.loansapp.data.datasource.TokenDataSource
import ru.focusstart.kireev.loansapp.data.datasource.TokenDataSourceImpl
import ru.focusstart.kireev.loansapp.data.network.ApiService
import ru.focusstart.kireev.loansapp.data.repository.CreateLoanRepositoryImpl
import ru.focusstart.kireev.loansapp.data.repository.ListLoansRepositoryImpl
import ru.focusstart.kireev.loansapp.data.repository.LoanDetailInfoRepositoryImpl
import ru.focusstart.kireev.loansapp.data.repository.RegistrationRepositoryImpl
import ru.focusstart.kireev.loansapp.domain.repository.CreateLoanRepository
import ru.focusstart.kireev.loansapp.domain.repository.ListLoansRepository
import ru.focusstart.kireev.loansapp.domain.repository.LoanDetailInfoRepository
import ru.focusstart.kireev.loansapp.domain.repository.RegistrationRepository
import ru.focusstart.kireev.loansapp.domain.scenario.CheckLoginValid
import ru.focusstart.kireev.loansapp.domain.scenario.CheckPasswordValid
import ru.focusstart.kireev.loansapp.domain.usecase.*
import ru.focusstart.kireev.loansapp.presentation.viewmodel.CreateLoanViewModel
import ru.focusstart.kireev.loansapp.presentation.viewmodel.DetailLoanInfoViewModel
import ru.focusstart.kireev.loansapp.presentation.viewmodel.ListLoansViewModel
import ru.focusstart.kireev.loansapp.presentation.viewmodel.EntryViewModel

val viewModelModule = module {
    viewModel { EntryViewModel(get(), get()) }
    viewModel { ListLoansViewModel(get()) }
    viewModel { CreateLoanViewModel(get(), get()) }
    viewModel { DetailLoanInfoViewModel(get()) }
}

val apiModule = module {
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
    single { provideApiService(get()) }
}

val netModule = module {
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://focusapp-env.eba-xm2atk2z.eu-central-1.elasticbeanstalk.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }
    single { provideRetrofit() }
}

val repositoryModule = module {
    fun provideRegistrationRepository(
        api: ApiService,
        dataSource: TokenDataSource
    ): RegistrationRepository {
        return RegistrationRepositoryImpl(api, dataSource)
    }

    fun provideListLoansRepository(
        api: ApiService,
        dataSource: TokenDataSource
    ): ListLoansRepository {
        return ListLoansRepositoryImpl(api, dataSource)
    }

    fun provideCreateLoanRepository(
        api: ApiService,
        dataSource: TokenDataSource
    ): CreateLoanRepository {
        return CreateLoanRepositoryImpl(api, dataSource)
    }

    fun provideLoanDetailInfoRepository(
        api: ApiService,
        dataSource: TokenDataSource
    ): LoanDetailInfoRepository {
        return LoanDetailInfoRepositoryImpl(api, dataSource)
    }

    single { provideRegistrationRepository(get(), get()) }
    single { provideListLoansRepository(get(), get()) }
    single { provideCreateLoanRepository(get(), get()) }
    single { provideLoanDetailInfoRepository(get(), get()) }
}

val dataSourceModule = module {
    fun provideTokenDataSource(context: Context): TokenDataSource {
        return TokenDataSourceImpl(context)
    }

    single { provideTokenDataSource(androidContext()) }
}

val useCasesModule = module {

    fun provideRegistrationUseCase(
        repo: RegistrationRepository,
        passwordValid: CheckPasswordValid,
        loginValid: CheckLoginValid
    ): RegistrationUseCase {
        return RegistrationUseCase(repo, passwordValid, loginValid)
    }

    fun provideLoginUseCase(
        repo: RegistrationRepository,
        passwordValid: CheckPasswordValid,
        loginValid: CheckLoginValid
    ): LoginUseCase {
        return LoginUseCase(repo, passwordValid, loginValid)
    }

    fun provideListLoansUseCase(repo: ListLoansRepository): ListLoansUseCase {
        return ListLoansUseCase(repo)
    }

    fun provideGetLoanConditionsUseCase(repo: CreateLoanRepository): GetLoanConditionsUseCase {
        return GetLoanConditionsUseCase(repo)
    }

    fun provideSendLoanUseCase(repo: CreateLoanRepository): SendLoanUseCase {
        return SendLoanUseCase(repo)
    }

    fun provideLoanDetailInfoUseCase(repo: LoanDetailInfoRepository): LoanDetailInfoUseCase {
        return LoanDetailInfoUseCase(repo)
    }

    fun provideCheckLoginValid(): CheckLoginValid {
        return CheckLoginValid()
    }

    fun provideCheckPasswordValid(): CheckPasswordValid {
        return CheckPasswordValid()
    }
    single { provideCheckLoginValid() }
    single { provideCheckPasswordValid() }
    single { provideRegistrationUseCase(get(), get(), get()) }
    single { provideLoginUseCase(get(), get(), get()) }
    single { provideListLoansUseCase(get()) }
    single { provideGetLoanConditionsUseCase(get()) }
    single { provideSendLoanUseCase(get()) }
    single { provideLoanDetailInfoUseCase(get()) }
}