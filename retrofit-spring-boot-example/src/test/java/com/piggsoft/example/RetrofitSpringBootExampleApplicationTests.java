package com.piggsoft.example;

import com.piggsoft.example.http.FlowService;
import com.piggsoft.example.http.GithubService;
import com.piggsoft.example.http.Repo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RetrofitSpringBootExampleApplication.class)
public class RetrofitSpringBootExampleApplicationTests {

	@Autowired
	private FlowService flowService;

	@Autowired
	private GithubService githubService;

	@Test
	public void test01() throws IOException {
		for (int i=0; i<10; i++) {
			long t1 = System.currentTimeMillis();
			Call<Object> call = flowService.getFlow("460036391887155");
			Response<Object> o = call.execute();
			System.out.println(o.body());
			System.out.println(System.currentTimeMillis() - t1);
		}

	}

	@Test
	public void test02 () throws IOException {
		for (int i=0; i<10; i++) {
			long t1 = System.currentTimeMillis();
			Call<List<Repo>> call = githubService.listRepos("piggsoft");
			Response<List<Repo>> o = call.execute();
			System.out.println(o.body());
			System.out.println(System.currentTimeMillis() - t1);
		}
	}

	@Test
	public void test03() throws IOException {
		test01();
		test02();
		test01();
		test02();
	}

}
