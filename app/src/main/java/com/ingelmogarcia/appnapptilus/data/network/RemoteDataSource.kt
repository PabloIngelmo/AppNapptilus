package com.ingelmogarcia.appnapptilus.data.network

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.ingelmogarcia.appnapptilus.HandleError
import com.ingelmogarcia.appnapptilus.core.ApiBook
import com.ingelmogarcia.appnapptilus.data.model.DataPageModel
import com.ingelmogarcia.appnapptilus.toError

class RemoteDataSource {


    suspend fun getDataPage(page: Int) : Either<HandleError, DataPageModel> {
///////////////////////////////////////////////////////////////////////////////////////////////////////
        //Gestion de errores mediante Either<L,R>
///////////////////////////////////////////////////////////////////////////////////////////////////////
        val response: Either<HandleError, DataPageModel> = try{
            ApiBook.service.getDataPage(page).right()
        }catch (e: Exception){
            e.toError().left()
        }

        return response






///////////////////////////////////////////////////////////////////////////////////////////////////////
                //Gestion de errores simplemente con try/catch
///////////////////////////////////////////////////////////////////////////////////////////////////////
       /*return  withContext(Dispatchers.IO){
            //val response = retrofit.create(ApiClient::class.java).getDataPage()
           try {
               val response = ApiBook.service.getDataPage()
               if(response.isSuccessful){
                   response.body()
               }else{
                   null
               }
           } catch (e:Exception){
               print("Error al llamar al servicio: " + e)
               null
           }
        }*/


///////////////////////////////////////////////////////////////////////////////////////////////////////
        //Gestion de errores con Call<> mediante Callback y onResponse y onFailure.
        //Para esto, ademas, el metodo getDataPage de ApiClient deberia retornar un Call<DataPageModel>
///////////////////////////////////////////////////////////////////////////////////////////////////////

        /*ApiBook.service.getDataPage().enqueue(object : Callback<DataPageModel>{
            override fun onResponse(call: Call<DataPageModel>, response: Response<DataPageModel>) {
                return response.body()
            }

            override fun onFailure(call: Call<DataPageModel>, t: Throwable) {
                return t.message
            }
        })*/
    }
}
