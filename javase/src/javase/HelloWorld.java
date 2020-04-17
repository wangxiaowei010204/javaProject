package javase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import charactor.Hero;

public class HelloWorld {

	public static void main(String[] args) {
		Random r = new Random();
		List<Hero> heros = new ArrayList<Hero>();
		for (int i = 0; i < 10; i++) {
			heros.add(new Hero("hero " + i, r.nextInt(1000), r.nextInt(100)));
		}

		System.out.println("��ʼ�����Ϻ������ (���һ�������ظ�)��");
		System.out.println(heros);

		// ��ͳ��ʽ
		Collections.sort(heros, new Comparator<Hero>() {
			@Override
			public int compare(Hero o1, Hero o2) {
				return (int) (o2.hp - o1.hp);
			}
		});

		Hero hero = heros.get(2);
		System.out.println("ͨ����ͳ��ʽ�ҳ�����hp�����ߵ�Ӣ��������:" + hero.name);

		// �ۺϷ�ʽ
		String name = heros.stream().sorted((h1, h2) -> h1.hp > h2.hp ? -1 : 1).skip(2).map(h -> h.getName())
				.findFirst().get();
		
		heros
		.stream()
		.filter(h -> h.hp > 100 && h.damage < 50)
		.forEach(h -> System.out.println(h.name));

		System.out.println("ͨ���ۺϲ����ҳ�����hp�����ߵ�Ӣ��������:" + name);
	}
}
