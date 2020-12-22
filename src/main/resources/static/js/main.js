
function getId(list, id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id == id) {
            return i
        }

    }
    return -1
}

var jewelApi = Vue.resource('/jewels/{id}');

Vue.component('jewelForm', {
    props: ['jewels'],
    data: function () {
      return {
          id: 0,
          brandName: '',
          collection: 'SPRING',
          cost: 0,
          warranty: 0
      }
    },
    template:
    '<div style="position: absolute; right: 0; text-align: center">' +
        '<h3 style="text-align: center">Введите информацию</h3>' +
        '<p>Если вы хотите обновить введите id</p>' +
        '<p>Если добавить не вводите</p>' +
        '<input type = "text" placeholder="id" v-model="id"/><br>' +
        '<input type = "text" placeholder="brandName" v-model="brandName"/><br>' +
        '<input type = "text" placeholder="collection" v-model="collection"/><br>' +
        '<input type = "text" placeholder="cost" v-model="cost"/><br>' +
        '<input type = "text" placeholder="warranty" v-model="warranty"/><br>' +
        '<input type="button" value="Save/Update" @click="save"/>' +
    '</div>',
    methods: {
        save: function () {
            const jewel = {
                id: this.id,
                brandName: this.brandName,
                collection: this.collection,
                cost: this.cost,
                warranty: this.warranty
            };
            if (getId(this.jewels, this.id) === -1) {
                jewelApi.save({}, jewel).then(result => result.json()
                    .then(data => {
                        this.jewels.push(data)
                    })
                )
            }
            else {
                jewelApi.update({}, jewel).then(result => result.json()
                    .then(data => {
                        this.jewels.splice(getId(this.jewels, this.id), 1, data);
                    }))
            }
        }
    }
})

Vue.component('jewelRow', {
    props: ['jewel', "jewels"],
    template:

        '<tr>' +
            '<td >{{jewel.id}}</td>' +
            '<td >{{jewel.brandName}}</td>' +
            '<td >{{jewel.collection}}</td>' +
            '<td >{{jewel.cost}}</td>' +
            '<td >{{jewel.warranty}}</td>' +
            '<td> <input type="button" value="X" @click="del"></td>' +
        '</tr>',
    methods: {
        del: function () {
            jewelApi.remove({id: this.jewel.id}).then(result => {
                if (result.ok) {
                    this.jewels.splice(this.jewels.indexOf(this.jewel), 1)
                }
            })
        }
    }

    }

)

Vue.component('jewelList', {
    props: ['jewels'],
    data: function () {
        return {
            id: 0,
        }
    },
    template: '<div style="position: relative; width: 1000px">' +
        '<div> Поиск по названию бредна: </div>' +
        '<label>Введите название </label>' +
        '<input type = "text" placeholder="price" v-model="id"/>' +
        '<input type="button" value="Search" @click="created"/>' +
        '<jewelForm :jewels="jewels"/>' +
        '<table style="">\n' +
        '        <tr>\n' +
        '            <th>Номер</th>\n' +
        '            <th>Название</th>\n' +
        '            <th>Коллекция</th>\n' +
        '            <th>Стоимость</th>\n' +
        '            <th>Гарантия</th>\n' +
        '        </tr>' +
        '<jewelRow v-for="jewel in jewels" :key="jewel.id" :jewel="jewel" :jewels="jewels" />' +
        '</table>' +
        '</div>',
    created: function () {
        this.jewels.splice(0, this.jewels.length);
        jewelApi.get(this.id).then(result =>
            result.json().then(data =>
                data.forEach(jewel => this.jewels.push(jewel))
            ))

    },
    methods: {
        created: function () {
            // this.jewel.splice(0, this.jewel.length);
            if (this.id != 0) {
                jewelApi.get({id: this.id}).then(result =>
                    result.json().then(data =>
                        data.forEach(jewel => this.jewels.push(jewel))
                    ))
            }
            else {
                jewelApi.get(this.id).then(result =>
                    result.json().then(data =>
                        data.forEach(jewel => this.jewels.push(jewel))
                    ))

            }
        }

    }
})



var app = new Vue({
    el: '#app',
    template: '<jewelList :jewels="jewels"/>',
    data: {
        jewels: []
    }
});